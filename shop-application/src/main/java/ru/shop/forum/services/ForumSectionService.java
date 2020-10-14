package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.repositories.ForumSectionRepository;

@Service
public class ForumSectionService extends AbstractForumEntityService<ForumSection, ForumSectionRepository> {
	
	
	public ForumSectionService(ForumSectionRepository repository) {
		super(repository);
		this.entityClass = ForumSection.class;
	}
}
