package ru.shop.security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.shop.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
	
	private static JwtService jwtService;
	
	@BeforeAll
	public static void beforeAll() {
		jwtService = new JwtService("SECRET_WORD", "SHOP", 3, "Europe/Moscow");
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
	
	@Test
	public void valid_UserDetails_Token_Should_Return_Valid_Role() {
		//given
		User user = new User();
		user.setEmail("user1@email.com");
		user.setEnabled(true);
		user.setRole(Roles.MODERATOR);
		
		ShopUserDetails userDetails = new ShopUserDetails(user);
		
		//when
		String jwt = jwtService.issueJwt(userDetails);
		
		//then
		assertEquals(Roles.MODERATOR.getAuthority(), jwtService.getAuthority(jwt));
	}
	
	@Test
	public void valid_UserDetails_Token_Should_Return_Valid_Username_As_Email() {
		//given
		String email = "user2@email.com";
		
		User user = new User();
		user.setEmail(email);
		user.setEnabled(true);
		user.setRole(Roles.MODERATOR);
		
		ShopUserDetails userDetails = new ShopUserDetails(user);
		
		//when
		String jwt = jwtService.issueJwt(userDetails);
		
		//then
		assertTrue(jwtService.validateToken(jwt));
		assertEquals(email, jwtService.getUsername(jwt));
	}
	
	@Test
	public void valid_UserDetails_Token_Should_Return_Valid_Username_As_Nickname() {
		//given
		String nickname = "usernick";
		
		User user = new User();
		user.setEmail("user3@email.com");
		user.setNickName(nickname);
		user.setEnabled(true);
		user.setRole(Roles.MODERATOR);
		
		ShopUserDetails userDetails = new ShopUserDetails(user);
		
		//when
		String jwt = jwtService.issueJwt(userDetails);
		
		//then
		assertTrue(jwtService.validateToken(jwt));
		assertEquals(nickname, jwtService.getUsername(jwt));
	}
	
}