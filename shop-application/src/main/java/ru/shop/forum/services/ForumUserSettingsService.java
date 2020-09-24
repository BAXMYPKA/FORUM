package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumUserSettings;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ForumUserSettingsService extends AbstractForumEntityService<ForumUserSettings, ForumEntityRepository<ForumUserSettings>> {
	
	
	public ForumUserSettingsService(ForumEntityRepository<ForumUserSettings> repository) {
		super(repository);
		this.entityClass = ForumUserSettings.class;
	}
}
