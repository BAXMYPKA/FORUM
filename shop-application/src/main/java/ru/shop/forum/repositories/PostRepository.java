package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.entities.User;
import ru.shop.forum.entities.Post;
import ru.shop.forum.repositories.ForumEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends ForumEntityRepository<Post> {
	
	Optional<Set<Post>> findAllByUser(User user);
	
	
}
