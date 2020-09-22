package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.Post;
import ru.shop.forum.entities.dto.PostDto;
import ru.shop.forum.services.PostService;

@RestController
@RequestMapping(path = "/posts")
public class PostRestController extends AbstractForumRestController<Post, PostDto, PostService> {
	@Override
	protected void setEntityClass(Class<Post> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	protected void setEntityDtoClass(Class<PostDto> entityDtoClass) {
		this.entityDtoClass = entityDtoClass;
	}
	
	@Override
	protected void setEntityService(PostService entityService) {
		this.entityService = entityService;
	}
}
