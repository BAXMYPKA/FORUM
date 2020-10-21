package ru.shop.services;

import lombok.Getter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.shop.entities.RegistrationConfirmationUuid;
import ru.shop.entities.User;
import ru.shop.repositories.UserRepository;

import javax.persistence.NoResultException;

@Getter
@Service
public class UserService extends AbstractEntityService<User, UserRepository> {
	
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		super(repository);
		this.entityClass = User.class;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * A strict saving of a User to the database.
	 * Allowed for {@link ru.shop.security.Roles#ADMIN} only.
	 * @param user
	 * @return Saved and enabled User
	 */
	@Override
//	@PreAuthorize(value = "#principal.authorities.contains('ADMIN')")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}
	
	/**
	 * Registration of a new User who is required to be self-confirmed by email.
	 * @param user A new not confirmed User
	 * @return Not enabled User with a newly created {@link ru.shop.entities.RegistrationConfirmationUuid}
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
	public User saveNewUnconfirmed(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(false);
		user.setRegistrationConfirmationUuid(new RegistrationConfirmationUuid(user));
		return repository.save(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Boolean existsUserByNickName(String nickname) {
		if (nickname == null || nickname.isBlank()) return false;
		return repository.existsUserByNickName(nickname);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public User findUserByNickname(String nickname) throws NoResultException {
		if (nickname == null || nickname.isBlank()) throw new NoResultException("No User could by found for null or empty name!");
		return repository.findByNickName(nickname).orElseThrow(() -> new NoResultException("No User found for name " + nickname));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public User findUserByEmail(String email) throws NoResultException {
		if (email == null || email.isBlank()) throw new NoResultException("No User could by found for null or empty email!");
		return repository.findByEmail(email).orElseThrow(() -> new NoResultException("No User found for email " + email));
	}
	
}
