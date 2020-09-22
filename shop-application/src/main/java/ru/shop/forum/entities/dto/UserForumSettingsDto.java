package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ForumUserSettings;

public class UserForumSettingsDto extends AbstractForumDto<ForumUserSettings> {
	@Override
	protected void setEntityClass(Class<ForumUserSettings> entityClass) {
		this.entityClass = entityClass;
	}
}
