package ru.shop.security.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.shop.repositories.UserRepository;
import ru.shop.security.JwtService;
import ru.shop.security.Roles;
import ru.shop.security.ShopUserDetailsService;
import ru.shop.security.UsernamePasswordJwtFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	@Order(2)
	@Configuration
	public static class ShopConfig extends WebSecurityConfigurerAdapter {
		
		private final String SHOP_AUTHENTICATION_URL = "/shop/authentication";
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private JwtService jwtService;
		
		/**
		 * AntMatchers syntax: https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.requiresChannel()
					.anyRequest()
					.requiresSecure()
					.and()
					.authorizeRequests()
					.antMatchers("/shop.ru/v?/admin/**").hasRole(Roles.ADMIN.getAuthority())
					.antMatchers("/shop.ru/v?/users").authenticated()
					.antMatchers("/shop.ru", "/shop.ru/v?").permitAll()
					.antMatchers("/resources/**").permitAll()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.and()
					.formLogin()
//				.loginPage("/login")
					.loginProcessingUrl(SHOP_AUTHENTICATION_URL)
					.defaultSuccessUrl("/shop.ru/")
					.permitAll()
					.and()
					.logout()
					.logoutUrl("/shop.ru/logout")
					.deleteCookies("JSESSIONID")
					.logoutSuccessUrl("/shop.ru")
					.permitAll();
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) {
			auth.authenticationProvider(authenticationProvider());
		}
		
		@Bean
		public UserDetailsService userDetailsService() {
			return new ShopUserDetailsService(userRepository);
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder(10);
		}
		
		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
			authenticationProvider.setPasswordEncoder(passwordEncoder());
			authenticationProvider.setUserDetailsService(userDetailsService());
			return authenticationProvider;
		}
		
		//The following method MUST be in a separate @Configuration class file!!!!!!!!!!!!!!!!!!!
		//Otherwise "java.lang.IllegalStateException: No ServletContext set" error will be raised.
/*
	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(getHttpConnector());
		return tomcat;
	}
	
	private Connector getHttpConnector() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8181);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}
*/
	
	}
	
	@Order(3)
	@Configuration
	public static class ShopForumConfig extends WebSecurityConfigurerAdapter {
		
		private final String FORUM_AUTHENTICATION_URL = "/shop/forum/authentication";
		
		@Autowired
		private JwtService jwtService;
		
		@Bean
		UsernamePasswordJwtFilter usernamePasswordJwtFilter() throws Exception {
			return new UsernamePasswordJwtFilter(
					jwtService, authenticationManager(), FORUM_AUTHENTICATION_URL);
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.requiresChannel()
					.anyRequest()
					.requiresSecure()
					.and()
					.authorizeRequests()
					.antMatchers("/shop/forum/v?/admin/**").hasAnyRole(Roles.ADMIN.getAuthority())
					.and()
					.authorizeRequests()
					.antMatchers("/shop/forum/login", "/shop/forum/v?/**").permitAll()
//				.antMatchers("/shop.ru/forum/", "/shop.ru/forum/v1.0").permitAll()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.formLogin()
					.loginPage("/shop/forum/login")
					.loginProcessingUrl(FORUM_AUTHENTICATION_URL)
					.defaultSuccessUrl("/shop.ru/forum/v1.0/")
					.permitAll()
					.and()
					.logout()
					.permitAll()
					.and()
					.addFilterAt(usernamePasswordJwtFilter(), UsernamePasswordAuthenticationFilter.class);
		}
	}
}
