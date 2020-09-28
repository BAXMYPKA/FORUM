package ru.shop.forum.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.entities.dto.PrivateMessageDto;
import ru.shop.forum.services.PrivateMessageService;

@RestController
@RequestMapping(path = "/v1.0/pms")
public class PrivateMessageRestController extends AbstractForumRestController<PrivateMessage, PrivateMessageDto, PrivateMessageService> {
	
	public PrivateMessageRestController(PrivateMessageService entityService, ModelMapper modelMapper) {
		super(entityService, modelMapper);
		this.entityDtoClass = PrivateMessageDto.class;
	}
}
