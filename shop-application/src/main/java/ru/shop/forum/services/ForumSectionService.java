package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ForumSectionService extends AbstractForumEntityService<ForumSection, ForumEntityRepository<ForumSection>> {
	
	@Override
	protected void setRepository(ForumEntityRepository<ForumSection> repository) {
		this.repository = repository;
	}
}
