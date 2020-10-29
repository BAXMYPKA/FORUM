package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.Post;
import ru.shop.forum.entities.dto.PostDto;
import ru.shop.forum.services.PostService;
import ru.shop.utils.ShopEventPublisher;

@RestController
@RequestMapping(path = "/forum/v1.0/posts")
public class PostRestController extends AbstractForumRestController<Post, PostDto, PostService> {
	
	public PostRestController(PostService entityService,
									  ModelMapper modelMapper,
									  ObjectMapper objectMapper,
									  ShopEventPublisher shopEventPublisher) {
		super(entityService, modelMapper, objectMapper, shopEventPublisher);
		this.entityDtoClass = PostDto.class;
	}
}
