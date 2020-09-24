package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class PrivateMessageService extends AbstractForumEntityService<PrivateMessage, ForumEntityRepository<PrivateMessage>> {
	
	public PrivateMessageService(ForumEntityRepository<PrivateMessage> repository) {
		super(repository);
		super.entityClass = PrivateMessage.class;
	}
}
