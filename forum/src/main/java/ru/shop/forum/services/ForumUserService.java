package ru.shop.forum.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumUser;
import ru.shop.forum.entities.ImgAvatar;
import ru.shop.forum.entities.UserForumSettings;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.repositories.ForumUserRepository;
import ru.shop.services.AbstractEntityService;
import ru.shop.services.UserService;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Also is in charge of {@link UserForumSettings} and {@link ImgAvatar} entities and their repositories
 */
@Getter
@Service
public class ForumUserService extends AbstractEntityService<ForumUser, ForumUserRepository> {
	
	@Autowired
	private ForumEntityRepository<ImgAvatar> imgAvatarRepository;
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public Boolean existsUserByNickName(String nickname) {
		return repository.existsUserByNickName(Objects.requireNonNullElse(nickname, ""));
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public ForumUser findUserByNickname(String nickname) throws NoResultException {
		return repository.findByNickName(Objects.requireNonNullElse(nickname, ""))
			  .orElseThrow(() -> new NoResultException("No User found for name " + nickname));
	}
	
	@Transactional(value = Transactional.TxType.SUPPORTS)
	public ForumUser findUserByEmail(String email) throws NoResultException {
		return repository.findByEmail(Objects.requireNonNullElse(email, ""))
			  .orElseThrow(() -> new NoResultException("No User found for email " + email));
	}
	
	@Override
	protected void setRepository(ForumUserRepository repository) {
		this.repository = repository;
	}
}
