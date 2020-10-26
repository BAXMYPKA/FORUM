package ru.shop.security;

import com.hazelcast.config.SecurityConfig;
import org.apache.catalina.util.SessionConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class HazelcastSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
	public HazelcastSecurityInitializer() {
		super(SecurityConfig.class, SessionConfig.class);
	}
	
}
