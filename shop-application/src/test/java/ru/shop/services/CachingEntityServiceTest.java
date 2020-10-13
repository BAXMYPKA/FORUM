package ru.shop.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shop.configs.CacheEventLogger;
import ru.shop.entities.User;
import ru.shop.entities.utils.Sex;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CachingEntityServiceTest {
	
	@Autowired
	private CacheEventLogger cacheEventLogger;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		//given
		User user = new User("user@email.com");
		user.setPassword("123");
		user.setFirstName("FirstName");
		user.setSex(Sex.MALE);
		user.setBirthdate(LocalDate.now().minusYears(20));
		user.setNickName("Nick");
		userService.save(user);
		
		//when
		Optional<User> foundUser = userService.findOne(user.getId());
		
		//then
		assertFalse(foundUser.isEmpty());
		assertEquals("Nick", foundUser.get().getNickName());
	}
}