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
	protected void setEntityClass(Class<PrivateMessage> entityClass) {
		this.entityClass = entityClass;
	}
	
	@Override
	protected void setEntityDtoClass(Class<PrivateMessageDto> entityDtoClass) {
		this.entityDtoClass = entityDtoClass;
	}
	
	@Override
	protected void setEntityService(PrivateMessageService entityService) {
		this.entityService = entityService;
	}
}
