package ru.shop.forum.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.User;
import ru.shop.forum.entities.UserForumSettings;
import ru.shop.forum.repositories.EntityRepository;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.repositories.UserRepository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Also is in charge of {@link UserForumSettings} and {@link ImgAvatar} entities and their repositories
 */
@Getter
@Service
public class UserService extends AbstractForumEntityService<User, UserRepository> {
	
	@Autowired
	private ForumEntityRepository<UserForumSettings> userForumSettingsRepository;
	
	@Autowired
	private ForumEntityRepository<ImgAvatar> imgAvatarRepository;
	
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
		this.repository = repository;
	}
	
}
