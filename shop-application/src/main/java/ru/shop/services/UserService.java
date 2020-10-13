package ru.shop.services;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shop.entities.User;
import ru.shop.repositories.UserRepository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Objects;

@Getter
@CacheConfig(cacheNames = "users")
@Service
public class UserService extends AbstractEntityService<User, UserRepository> {
	
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		super(repository);
		this.entityClass = User.class;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	@Transactional(value = Transactional.TxType.REQUIRED)
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Boolean existsUserByNickName(String nickname) {
		return repository.existsUserByNickName(Objects.requireNonNullElse(nickname, ""));
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public User findUserByNickname(String nickname) throws NoResultException {
		return repository.findByNickName(Objects.requireNonNullElse(nickname, ""))
			  .orElseThrow(() -> new NoResultException("No User found for name " + nickname));
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public User findUserByEmail(String email) throws NoResultException {
		return repository.findByEmail(Objects.requireNonNullElse(email, ""))
			  .orElseThrow(() -> new NoResultException("No User found for email " + email));
	}
	
}
