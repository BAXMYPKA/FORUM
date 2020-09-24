package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.Post;
import ru.shop.forum.repositories.PostRepository;

@Service
public class PostService extends AbstractForumEntityService<Post, PostRepository> {
	
	public PostService(PostRepository repository) {
		super(repository);
		this.entityClass = Post.class;
	}
}
