package ru.shop.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
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

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Configuration
	public static class ForumSecurityConfig extends WebSecurityConfigurerAdapter {
		
		/**
		 * AntMatchers syntax: https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
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
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopUserDetailsService(userService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
