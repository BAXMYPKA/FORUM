package ru.shop.repositories;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryCustom <User, Long> {
	
	Optional<User> findByEmail(String email, String namedEntityGraph);
	
	Optional<User> findByNickName(String nickname, String namedEntityGraph);
	
}
