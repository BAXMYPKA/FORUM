package ru.shop.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.shop.entities.User;
import ru.shop.repositories.UserRepository;
import ru.shop.security.configs.SecurityConfig;

import javax.persistence.NoResultException;

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
	 * @throws BadCredentialsException   If a found {@link User} is not activated (contains {@link User#getRegistrationConfirmationUuid()}).
	 *                                   As activated Users must not contain it.
	 */
	@Override
	public UserDetails loadUserByUsername(String nickNameOrEmail) throws UsernameNotFoundException, BadCredentialsException {
		if (nickNameOrEmail == null || nickNameOrEmail.isBlank())
			throw new UsernameNotFoundException("Nickname or email is null or empty!");
		
		User user = userRepository.findByEmail(nickNameOrEmail, "new-user-with-registrationUuid")
			.orElseGet(() -> userRepository.findByNickName(nickNameOrEmail, "new-user-with-registrationUuid")
				.orElseThrow(() -> new NoResultException("No user found for = " + nickNameOrEmail)));
		
		if (!user.isEnabled() || user.getRegistrationConfirmationUuid() != null) {
			throw new BadCredentialsException("User with " + nickNameOrEmail + " is not activated!");
		}
		return new ShopUserDetails(user);
	}
}

