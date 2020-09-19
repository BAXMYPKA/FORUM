package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.PrivateMessage;

public class PrivateMessageDto extends AbstractForumDto<PrivateMessage> {
	@Override
	protected void setAbstractEntityClass(Class<PrivateMessage> abstractEntityClass) {
		this.abstractEntityClass = abstractEntityClass;
	}
}
