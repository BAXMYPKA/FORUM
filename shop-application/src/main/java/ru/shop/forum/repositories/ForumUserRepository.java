package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.entities.User;
import ru.shop.forum.entities.ForumUser;
import ru.shop.repositories.EntityRepository;

import java.util.Optional;

@Repository
public interface ForumUserRepository extends EntityRepository<ForumUser> {
	
	boolean existsUserByNickName(String nickName);
	
	Optional<ForumUser> findByEmail(String email);

//	User getUserByEmail(String email);
	
	Optional<ForumUser> findByNickName(String nickname);
	
}
