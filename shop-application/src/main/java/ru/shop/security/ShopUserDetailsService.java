package ru.shop.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.shop.entities.User;
import ru.shop.repositories.UserRepository;
import ru.shop.security.configs.SecurityConfig;
import ru.shop.services.UserService;

import javax.persistence.NoResultException;
import java.util.Objects;
import java.util.Optional;

public class ShopUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	/**
	 * @param userRepository bean ss included in {@link SecurityConfig#userDetailsService()}
	 */
	public ShopUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * @param nickNameOrEmail {@link User#getNickName()} or {@link User#getEmail()}
	 * @return {@link ShopUserDetails} with {@link ShopUserDetails#setUser(User)} }
	 * @throws UsernameNotFoundException If no {@link User} found neither for nickname nor for email
	 */
	@Override
	public UserDetails loadUserByUsername(String nickNameOrEmail) throws UsernameNotFoundException {
		if (nickNameOrEmail == null || nickNameOrEmail.isBlank())
			throw new UsernameNotFoundException("Nickname or email is null or empty!");
		
		User user = userRepository.findByNickName(nickNameOrEmail)
				.orElseGet(() -> userRepository.findByNickName(nickNameOrEmail)
						.orElseThrow(() -> new NoResultException("No user found for = " + nickNameOrEmail)));
		return new ShopUserDetails(user);
	}
}

