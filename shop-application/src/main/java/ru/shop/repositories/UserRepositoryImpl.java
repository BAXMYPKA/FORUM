package ru.shop.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.shop.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom<User, Long> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<User> findByEmail(String email, String namedEntityGraph) {
		try {
			User user = entityManager.createQuery("SELECT u FROM User WHERE u.email= :email", User.class)
				.setParameter("email", email)
				.setHint("javax.persistence.fetchgraph", entityManager.getEntityGraph(namedEntityGraph))
				.getSingleResult();
			return Optional.of(user);
		} catch (NoResultException e) {
			log.debug("No User found with email={}", email);
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<User> findByNickName(String nickname, String namedEntityGraph) {
		try {
			User user = entityManager.createQuery("SELECT u FROM User WHERE u.nickName= :nickName", User.class)
				.setParameter("nickName", nickname)
				.setHint("javax.persistence.fetchgraph", entityManager.getEntityGraph(namedEntityGraph))
				.getSingleResult();
			return Optional.of(user);
		} catch (NoResultException e) {
			log.debug("No User found with nickName={}", nickname);
			return Optional.empty();
		}
	}
}
