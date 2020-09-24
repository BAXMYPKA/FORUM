package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.Post;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class PostService extends AbstractForumEntityService<Post, ForumEntityRepository<Post>> {
	
	public PostService(ForumEntityRepository<Post> repository) {
		super(repository);
		this.entityClass = Post.class;
	}
}
