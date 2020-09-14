package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends EntityRepository<User, Long> {
	
	boolean existsUserByNickName(String nickName);
	
	Optional<User> findByEmail(String email);

//	User getUserByEmail(String email);
	
	Optional<User> findByNickName(String nickname);
}
