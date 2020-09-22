package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.PrivateMessage;

public class PrivateMessageDto extends AbstractForumDto<PrivateMessage> {
	@Override
	protected void setEntityClass(Class<PrivateMessage> entityClass) {
		this.entityClass = entityClass;
	}
}
