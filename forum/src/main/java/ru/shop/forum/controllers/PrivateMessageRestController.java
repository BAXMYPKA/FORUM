package ru.shop.forum.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.entities.dto.PrivateMessageDto;
import ru.shop.forum.services.PrivateMessageService;

@RestController
@RequestMapping(path = "/pms")
public class PrivateMessageRestController extends AbstractForumRestController<PrivateMessage, PrivateMessageDto, PrivateMessageService> {
	@Override
	protected void setForumEntityClass(Class<PrivateMessage> forumEntityClass) {
		this.forumEntityClass = forumEntityClass;
	}
	
	@Override
	protected void setForumEntityDtoClass(Class<PrivateMessageDto> forumEntityDtoClass) {
		this.forumEntityDtoClass = forumEntityDtoClass;
	}
	
	@Override
	protected void setForumEntityService(PrivateMessageService forumEntityService) {
		this.forumEntityService = forumEntityService;
	}
}
