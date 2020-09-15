package ru.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
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
	
/*
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
*/

}
