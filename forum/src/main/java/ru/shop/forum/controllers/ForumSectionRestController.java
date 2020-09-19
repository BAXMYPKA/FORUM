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
	protected void setForumEntityClass(Class<ForumSection> forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<ForumSectionDto> forumEntityDtoClass) {
		this.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(ForumSectionService forumEntityService) {
		this.forumEntityService = forumEntityService;
	}
}
