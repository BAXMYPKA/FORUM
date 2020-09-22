package ru.shop.forum.services;

import org.springframework.stereotype.Service;
import ru.shop.forum.entities.UserForumSettings;
import ru.shop.forum.repositories.ForumEntityRepository;

@Service
public class ForumUserSettingsService extends AbstractForumEntityService<UserForumSettings, ForumEntityRepository<UserForumSettings>> {
	@Override
	protected void setRepository(ForumEntityRepository<UserForumSettings> repository) {
		this.repository = repository;
	}
}
