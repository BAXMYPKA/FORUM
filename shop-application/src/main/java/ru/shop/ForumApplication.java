package ru.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
	value =
		{@PropertySource(encoding = "UTF-8", value = "classpath:application.properties")}
)
public class ForumApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
	
}
