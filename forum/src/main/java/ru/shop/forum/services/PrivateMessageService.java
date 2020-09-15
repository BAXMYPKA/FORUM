package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.PrivateMessage;
import ru.shop.forum.repositories.EntityRepository;

@Service
public class PrivateMessageService extends AbstractEntityService<PrivateMessage, EntityRepository<PrivateMessage, Long>> {
	
	@Override
	protected void setRepository(EntityRepository<PrivateMessage, Long> repository) {
		super.repository = repository;
	}
}
