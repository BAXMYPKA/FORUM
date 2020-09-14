package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.User;

@Repository
public interface UserRepository extends EntityRepository<User, Long> {

	boolean existsUserByNickName(String nickName);
}
