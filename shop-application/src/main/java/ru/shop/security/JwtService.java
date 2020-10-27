package ru.shop.security;

import io.jsonwebtoken.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
	
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	private final Key key;
	
	@Setter
	@Value("${jwt.token.secret-word}")
	private String secretWord;
	@Setter
	@Value("${jwt.token.issuer}")
	private String issuer;
	
	@Setter
	@Value("${jwt.token.expiration.days}")
	private Integer tokenExpirationDays;
	
	@Setter
	@Value("${system.current.time-zone}")
	private String currentTimeZone;
	
	public JwtService() {
		key = new SecretKeySpec(secretWord.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getValue());
	}
	
	public JwtService(String secretWord, String issuer, Integer tokenExpirationDays, String currentTimeZone) {
		this.secretWord = secretWord;
		this.issuer = issuer;
		this.tokenExpirationDays = tokenExpirationDays;
		this.currentTimeZone = currentTimeZone;
		key = new SecretKeySpec(secretWord.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getValue());
	}
	
	public String issueJwt(ShopUserDetails userDetails) {
		if (!userDetails.isEnabled())
			throw new BadCredentialsException("Account=" + userDetails.getUsername() + " is not enabled!");
		
		return Jwts.builder()
				.signWith(signatureAlgorithm, key)
				.setSubject(userDetails.getUsername())
				.setExpiration(Date.from(LocalDate.now().atStartOfDay(ZoneId.of(currentTimeZone)).plusDays(tokenExpirationDays).toInstant()))
				.setIssuedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.of(currentTimeZone)).toInstant()))
				.claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
				.compact();
	}
	
	public boolean validateToken(String jwt) {
		try {
			Jwt parsedJwt = Jwts.parser().setSigningKey(key).parse(jwt);
			return true;
		} catch (ExpiredJwtException e) {
			log.debug(e.getMessage(), e);
		} catch (MalformedJwtException e) {
			log.debug(e.getMessage(), e);
		} catch (SignatureException e) {
			log.debug(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.debug(e.getMessage(), e);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return false;
	}
	
	public String getUsername(String jwt) {
		Claims claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
		return claimsJws.getSubject();
	}
	
	public String getAuthority(String jwt) {
		return (String) Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().get("role");
	}
}
