package ru.shop.security.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
import ru.shop.repositories.UserRepository;
import ru.shop.security.ShopUserDetailsService;

@Slf4j
@Configuration
//@ComponentScan(basePackages = {"ru.shop.security"})
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * AntMatchers syntax: https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.csrf().disable()
			.requiresChannel()
			.anyRequest()
			.requiresSecure()
			.and()
			.authorizeRequests()
			.antMatchers("/shop.ru/forum/v?/admin/**").authenticated()
			.antMatchers("/shop.ru/forum/", "/shop.ru/forum/v1.0").permitAll()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin()
			.loginPage("/shop.ru/forum/v1.0/login")
			.successForwardUrl("/shop.ru/forum/v1.0/")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
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
//	@Configuration
//	@Order(1)
//	public static class ShopSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	}
}
