package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.repositories.EntityRepository;

@Service
public class ForumSectionService extends AbstractForumEntityService<ForumSection, EntityRepository<ForumSection, Long>> {
	
	@Override
	protected void setRepository(EntityRepository<ForumSection, Long> repository) {
		super.repository = repository;
	}
}
