package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shop.entities.User;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ForumSectionService extends AbstractForumEntityService<ForumSection, ForumEntityRepository<ForumSection>> {
	
	
	public ForumSectionService(ForumEntityRepository<ForumSection> repository) {
		super(repository);
		this.entityClass = ForumSection.class;
	}
}
