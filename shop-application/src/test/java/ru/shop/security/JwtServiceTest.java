package ru.shop.security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shop.entities.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
	
	private static JwtService jwtService = new JwtService();
	
	@BeforeAll
	public static void beforeAll() {
		jwtService.setCurrentTimeZone("Europe/Moscow");
		jwtService.setIssuer("SHOP");
		jwtService.setSecretWord("SECRET_WORD");
		jwtService.setTokenExpirationDays(3);
	}
	
	@Test
	public void valid_UserDetails_Token_Should_Be_Validated() {
		//given
		User user = new User();
		user.setEmail("user@email.com");
		user.setEnabled(true);
		user.setRole(Roles.USER);
		
		ShopUserDetails userDetails = new ShopUserDetails(user);
		
		//when
		String jwt = jwtService.issueJwt(userDetails);
		
		//then
		assertTrue(jwtService.validateToken(jwt));
	}
}