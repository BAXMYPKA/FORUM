package ru.shop;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(1)
@TestConfiguration
public class TestSecurityConfigs extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.passwordEncoder(passwordEncoder)
			.withUser("Admin")
			.password(passwordEncoder.encode("Password"))
			.roles("ADMIN");
	}
	
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
	
	
}
