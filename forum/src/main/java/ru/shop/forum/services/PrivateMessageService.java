package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.repositories.EntityRepository;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class PrivateMessageService extends AbstractForumEntityService<PrivateMessage, ForumEntityRepository<PrivateMessage>> {
	
	@Override
	protected void setRepository(ForumEntityRepository<PrivateMessage> repository) {
		this.repository = repository;
	}
}
