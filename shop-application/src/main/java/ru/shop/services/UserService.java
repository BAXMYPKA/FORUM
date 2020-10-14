package ru.shop.services;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
