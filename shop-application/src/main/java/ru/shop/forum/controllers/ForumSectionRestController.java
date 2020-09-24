package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.entities.dto.ForumSectionDto;
import ru.shop.forum.services.ForumSectionService;

@RestController
@RequestMapping(path = "/v1.0/forum-sections")
public class ForumSectionRestController extends AbstractForumRestController<ForumSection, ForumSectionDto, ForumSectionService> {
	
	
	public ForumSectionRestController(ForumSectionService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = ForumSectionDto.class;
	}
}
