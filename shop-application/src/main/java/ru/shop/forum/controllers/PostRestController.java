package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.Post;
import ru.shop.forum.entities.dto.PostDto;
import ru.shop.forum.services.PostService;

@RestController
@RequestMapping(path = "/v1.0/posts")
public class PostRestController extends AbstractForumRestController<Post, PostDto, PostService> {
	
	public PostRestController(PostService entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
		this.entityDtoClass = PostDto.class;
	}
}
