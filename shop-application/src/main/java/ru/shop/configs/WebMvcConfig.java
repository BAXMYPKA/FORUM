package ru.shop.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc //https://stackoverflow.com/questions/41543549/enablewebmvc-throws-servletexception-could-not-resolve-view-with-name
public class WebMvcConfig implements WebMvcConfigurer {
	
/*
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(viewResolver());
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		viewResolver.setViewClass();
		viewResolver.setSuffix(".html");
		viewResolver.setPrefix("src/main/resources/dist/");
		return viewResolver;
	}
*/
}
