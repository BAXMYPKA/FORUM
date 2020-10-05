package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ImgAvatar;

public class ImgAvatarDto extends AbstractForumDto<ImgAvatar> {
	public void setEntityClass(Class<ImgAvatar> entityClass) {
		this.entityClass = entityClass;
	}
}
