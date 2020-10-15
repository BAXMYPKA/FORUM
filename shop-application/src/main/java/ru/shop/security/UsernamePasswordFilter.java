package ru.shop.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {
	public UsernamePasswordFilter() {
		super();
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		if (username == null) {
			username = "";
		}
		
		if (password == null) {
			password = "";
		}
		
		username = username.trim();
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		
/*
		Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);
		response.addHeader("Auth Bearer", "JWT token");
*/
		
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	@Override
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		super.setDetails(request, authRequest);
	}
	
	@Override
	public void setUsernameParameter(String usernameParameter) {
		super.setUsernameParameter(usernameParameter);
	}
	
	@Override
	public void setPasswordParameter(String passwordParameter) {
		super.setPasswordParameter(passwordParameter);
	}
	
	@Override
	public void setPostOnly(boolean postOnly) {
		super.setPostOnly(postOnly);
	}
}
