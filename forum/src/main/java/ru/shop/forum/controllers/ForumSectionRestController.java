package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.entities.dto.ForumSectionDto;
import ru.shop.forum.services.ForumSectionService;

@RestController
@RequestMapping(path = "/forum-sections")
public class ForumSectionRestController extends AbstractForumRestController<ForumSection, ForumSectionDto, ForumSectionService> {
	
	@Override
	protected void setEntityClass(Class<ForumSection> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	protected void setEntityDtoClass(Class<ForumSectionDto> entityDtoClass) {
		this.entityDtoClass = entityDtoClass;
	}
	
	@Override
	protected void setEntityService(ForumSectionService entityService) {
		this.entityService = entityService;
	}
}
