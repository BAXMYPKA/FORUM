package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.ForumUserSettings;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ForumUserSettingsService extends AbstractForumEntityService<ForumUserSettings, ForumEntityRepository<ForumUserSettings>> {
	@Override
	protected void setRepository(ForumEntityRepository<ForumUserSettings> repository) {
		this.repository = repository;
	}
}
