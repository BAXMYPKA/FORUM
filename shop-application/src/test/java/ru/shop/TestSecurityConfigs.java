package ru.shop;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import ru.shop.forum.controllers.ForumIndexController;
import ru.shop.forum.controllers.ForumUserRestController;

@Order(2)
@TestConfiguration
//@ComponentScan(basePackageClasses = {ForumIndexController.class, ForumUserRestController.class})
public class TestSecurityConfigs extends WebSecurityConfigurerAdapter {
	
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.requiresChannel()
				.anyRequest()
				.requiresSecure()
				.and()
				.authorizeRequests()
				.antMatchers("/shop.ru/forum/v?/user/**", "/shop.ru/forum/v?/admin/**").authenticated()
				.antMatchers("/shop.ru/forum/", "/shop.ru/forum/v?").permitAll()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
//				.httpBasic();
				.formLogin()
				.loginPage("/shop.ru/forum/v1.0/login")
				.successHandler(new ForwardAuthenticationSuccessHandler("/shop.ru/forum/v1.0"))
				.permitAll()
				.and()
				.logout()
				.permitAll();
	}
*/
	
/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.passwordEncoder(passwordEncoder)
			.withUser("Admin")
			.password(passwordEncoder.encode("Password"))
			.roles("ADMIN");
	}
*/

/*
	@Order(2)
	@TestConfiguration
	public static class TestForumSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/shop.ru/forum/v?/user/**", "/shop.ru/forum/v?/admin/**")
				.authenticated()
				.antMatchers("/shop.ru/forum/", "/shop.ru/forum/v?").permitAll()
				.and()
				.httpBasic();
		}
		
	}
*/

	
}
