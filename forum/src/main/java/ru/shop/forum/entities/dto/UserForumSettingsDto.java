package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.UserForumSettings;

public class UserForumSettingsDto extends AbstractForumDto<UserForumSettings> {
	@Override
	protected void setAbstractEntityClass(Class<UserForumSettings> abstractEntityClass) {
		this.abstractEntityClass = abstractEntityClass;
	}
}
