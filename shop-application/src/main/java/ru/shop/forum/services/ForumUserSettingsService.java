package ru.shop.forum.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumUserSettings;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.forum.repositories.ForumUserSettingsRepository;

@Service
public class ForumUserSettingsService extends AbstractForumEntityService<ForumUserSettings, ForumUserSettingsRepository> {
	
	
	public ForumUserSettingsService(ForumUserSettingsRepository repository) {
		super(repository);
		this.entityClass = ForumUserSettings.class;
	}
}
