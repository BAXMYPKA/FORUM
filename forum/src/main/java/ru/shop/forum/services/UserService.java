package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.User;
import ru.shop.forum.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserService extends AbstractEntityService<User, UserRepository> {

	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Boolean existsUserByNickName(String nickname) {
		return repository.existsUserByNickName(Objects.requireNonNullElse(nickname, ""));
	}
	
	@Override
	protected void setRepository(UserRepository repository) {
		super.repository = repository;
	}
}
