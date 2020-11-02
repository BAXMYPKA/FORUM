package ru.shop.configs;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.shop.repositories", "ru.shop.forum.repositories"})
@EntityScan(basePackages = {"ru.shop.entities", "ru.shop.forum.entities"})
@EnableTransactionManagement
public class JpaConfig {
	
	@Value("${h2.port}")
	private String h2port;
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2port);
	}
}
