package ru.shop.configs;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//@EnableWebMvc
public class WebMvcConfig {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
		"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/**")
//		.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//	}
	
//	@Bean
//	public InternalResourceViewResolver defaultViewResolver() {
//		return new InternalResourceViewResolver();
//	}
}
