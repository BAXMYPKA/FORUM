package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.repositories.PrivateMessageRepository;

@Service
public class PrivateMessageService extends AbstractForumEntityService<PrivateMessage, PrivateMessageRepository> {
	
	public PrivateMessageService(PrivateMessageRepository repository) {
		super(repository);
		super.entityClass = PrivateMessage.class;
	}
}
