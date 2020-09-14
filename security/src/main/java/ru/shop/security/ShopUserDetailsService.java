package ru.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.User;
import ru.shop.forum.services.UserService;

import javax.persistence.NoResultException;
import java.util.Objects;

@Service
public class ShopUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	/**
	 * @param nickNameOrEmail {@link User#getNickName()} or {@link User#getEmail()}
	 * @return {@link ShopUserDetails} with {@link ShopUserDetails#setUser(User)}
	 * @throws UsernameNotFoundException If no {@link User} found neither for nickname nor for email
	 */
	@Override
	public UserDetails loadUserByUsername(String nickNameOrEmail) throws UsernameNotFoundException {
		nickNameOrEmail = Objects.requireNonNullElse(nickNameOrEmail, "");
		User user = null;
		try {
			user = userService.findUserByNickname(nickNameOrEmail);
		} catch (NoResultException e) {
			user = userService.findUserByEmail(nickNameOrEmail);
		} finally {
			if (user != null) {
				return new ShopUserDetails(user);
			} else {
				throw new UsernameNotFoundException("No user found for = " + nickNameOrEmail);
			}
		}
	}
}
