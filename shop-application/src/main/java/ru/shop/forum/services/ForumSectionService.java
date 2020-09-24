package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shop.entities.User;
import ru.shop.forum.entities.ForumSection;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.repositories.ForumSectionRepository;

@Service
public class ForumSectionService extends AbstractForumEntityService<ForumSection, ForumSectionRepository> {
	
	
	public ForumSectionService(ForumSectionRepository repository) {
		super(repository);
		this.entityClass = ForumSection.class;
	}
}
