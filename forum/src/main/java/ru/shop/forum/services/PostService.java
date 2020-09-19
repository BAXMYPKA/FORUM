package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.Post;
import ru.shop.forum.repositories.EntityRepository;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class PostService extends AbstractForumEntityService<Post, ForumEntityRepository<Post>> {
	
	@Override
	protected void setRepository(ForumEntityRepository<Post> repository) {
		this.repository = repository;
	}
}
