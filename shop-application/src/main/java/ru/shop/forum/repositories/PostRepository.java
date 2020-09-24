package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.Post;
import ru.shop.forum.repositories.ForumEntityRepository;

@Repository
public interface PostRepository extends ForumEntityRepository<Post> {
}
