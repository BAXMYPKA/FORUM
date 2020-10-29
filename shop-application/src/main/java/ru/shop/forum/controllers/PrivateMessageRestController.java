package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.entities.dto.PrivateMessageDto;
import ru.shop.forum.services.PrivateMessageService;
import ru.shop.utils.ShopEventPublisher;

@RestController
@RequestMapping(path = "/forum/v1.0/pms")
public class PrivateMessageRestController extends AbstractForumRestController<PrivateMessage, PrivateMessageDto, PrivateMessageService> {
	
	public PrivateMessageRestController(PrivateMessageService entityService,
													ModelMapper modelMapper,
													ObjectMapper objectMapper,
													ShopEventPublisher shopEventPublisher) {
		super(entityService, modelMapper, objectMapper, shopEventPublisher);
		this.entityDtoClass = PrivateMessageDto.class;
	}
}
