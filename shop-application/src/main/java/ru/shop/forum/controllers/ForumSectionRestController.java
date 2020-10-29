package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.entities.dto.ForumSectionDto;
import ru.shop.forum.services.ForumSectionService;
import ru.shop.utils.ShopEventPublisher;

@RestController
@RequestMapping(path = "/forum/v1.0/forum-sections")
public class ForumSectionRestController extends AbstractForumRestController<ForumSection, ForumSectionDto, ForumSectionService> {
	
	
	public ForumSectionRestController(ForumSectionService entityService,
												 ModelMapper modelMapper,
												 ObjectMapper objectMapper,
												 ShopEventPublisher shopEventPublisher) {
		super(entityService, modelMapper, objectMapper, shopEventPublisher);
		this.entityDtoClass = ForumSectionDto.class;
	}
}
