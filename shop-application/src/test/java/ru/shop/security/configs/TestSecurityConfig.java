package ru.shop.security.configs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@Order(1)
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {
	
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.requiresChannel()
//			.anyRequest()
//			.requiresSecure()
//			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/shop.ru/forum/v?/admin/**").authenticated()
			.antMatchers("/shop.ru/forum/", "/shop.ru/forum/v1.0").permitAll()
			.and()
//			.sessionManagement()
//			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
			.formLogin()
			.loginPage("/shop.ru/forum/v1.0/login")
			.successForwardUrl("/shop.ru/forum/v1.0/")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}
*/

/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("Admin")
			.password(encoder.encode("Password"))
			.roles("ADMIN");
	}
*/
}
