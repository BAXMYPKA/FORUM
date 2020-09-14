package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.Post;
import ru.shop.forum.repositories.EntityRepository;

@Service
public class PostService extends AbstractEntityService<Post, EntityRepository<Post, Long>> {
	
	@Override
	protected void setRepository(EntityRepository<Post, Long> repository) {
		super.repository = repository;
	}
}
