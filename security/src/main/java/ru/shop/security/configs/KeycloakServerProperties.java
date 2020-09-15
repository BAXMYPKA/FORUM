package ru.shop.security.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
//@Configuration
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties {
	
	String contextPath = "/auth";
	String realmImportFile = "shopRealm.json";
	KeycloakAdmin adminUser = new KeycloakAdmin();
	
	@Getter
	@Setter
	public static class KeycloakAdmin {
		String username = "Admin";
		String password = "Admin";
	}
}
