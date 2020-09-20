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
	protected void setForumEntityClass(Class<Post> forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<PostDto> forumEntityDtoClass) {
		this.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(PostService forumEntityService) {
		this.forumEntityService = forumEntityService;
	}
}
