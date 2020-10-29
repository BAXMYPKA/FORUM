package ru.shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Is used for intercept username:password credentials after login process.
 * If credentials are valid inserts "Authentication: Bearer [jwt]" Header into HttpServletResponse.
 */
public class UsernamePasswordJwtFilter extends UsernamePasswordAuthenticationFilter {
	
	private JwtService jwtService;
	
	public UsernamePasswordJwtFilter() {
		super();
//		super(new AntPathRequestMatcher("/login", "POST"));
	}
	
	public UsernamePasswordJwtFilter(JwtService jwtService) {
		this();
		this.jwtService = jwtService;
	}
	
//	public UsernamePasswordJwtFilter() {
//		super(new AntPathRequestMatcher("/login", "POST"));
//	}
	
	
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
//		return super.requiresAuthentication(request, response);
		return true;
	}
	
	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		super.doFilter(req, res, chain);
//	}
	
//	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response, FilterChain chain, Authentication authResult)
		throws IOException, ServletException {
		
		super.successfulAuthentication(request, response, chain, authResult);
		
		String jwt;
		
		//Principal should be a ShopUserDetails
		if (authResult.getPrincipal() instanceof ShopUserDetails) {
			ShopUserDetails principal = (ShopUserDetails) authResult.getPrincipal();
			jwt = jwtService.issueJwt(principal);
		} else {
			jwt = jwtService.issueJwt(authResult.getName(), authResult.getAuthorities().iterator().next());
		}
		response.addHeader("Authentication", "Bearer " + jwt);
	}
	
}
