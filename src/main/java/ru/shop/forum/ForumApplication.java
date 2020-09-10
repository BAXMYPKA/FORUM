package ru.shop.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
	value =
		{@PropertySource(encoding = "UTF-8", value = "classpath:application.properties")}
)
@EntityScan(basePackages = "ru.shop.forum.entities")
public class ForumApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
	
}
