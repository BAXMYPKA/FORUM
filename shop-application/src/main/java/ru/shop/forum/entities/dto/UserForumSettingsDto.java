package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ForumUserSettings;

public class UserForumSettingsDto extends AbstractForumDto<ForumUserSettings> {
	public void setEntityClass(Class<ForumUserSettings> entityClass) {
		this.entityClass = entityClass;
	}
}
