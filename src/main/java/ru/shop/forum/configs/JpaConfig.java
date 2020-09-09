package ru.shop.forum.configs;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class JpaConfig {
	
	@Value("${h2.port}")
	private String h2port;
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException {
		System.out.println("###################"+h2port);
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2port);
	}
}
