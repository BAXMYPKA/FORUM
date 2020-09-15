package ru.shop.security.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shop.forum.services.UserService;
import ru.shop.security.ShopUserDetailsService;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(KeycloakServerProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopUserDetailsService(userService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
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
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(
			ServerProperties serverProperties, KeycloakServerProperties keycloakServerProperties) {
		return (evt) -> {
			Integer port = serverProperties.getPort();
			String keycloakContextPath = keycloakServerProperties.getContextPath();
			log.warn("Embedded Keycloak started: http://localhost:{}{} to use keycloak",
					port, keycloakContextPath);
		};
	}
	@Configuration
	public static class ForumSecurityConfig extends WebSecurityConfigurerAdapter {
		
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
				.antMatchers("/forum.shop.ru/v.?/user/**", "/forum.shop.ru/v.?/admin/**")
				.authenticated()
				.antMatchers("/forum.shop.ru/v.?").permitAll()
				.and()
				.formLogin()
				.loginPage("/forum.shop.ru/v.?/login")
				.permitAll()
				.and()
				.logout()
				.permitAll();
		}
		
	}
	
	@Configuration
	public static class ShopSecurityConfig extends WebSecurityConfigurerAdapter {
	}
	}
