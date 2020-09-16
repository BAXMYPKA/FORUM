package ru.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@Slf4j
@PropertySources(
		value =
				{@PropertySource(encoding = "UTF-8", value = "classpath:application.properties")}
)
public class ShopApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
	
}
