package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.User;
import ru.shop.forum.repositories.UserRepository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserService extends AbstractEntityService<User, UserRepository> {
	
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
	
	@Override
	protected void setRepository(UserRepository repository) {
		super.repository = repository;
	}
}
