package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ImgAvatar;

public class ImgAvatarDto extends AbstractForumDto<ImgAvatar> {
	@Override
	protected void setAbstractEntityClass(Class<ImgAvatar> abstractEntityClass) {
		this.abstractEntityClass = abstractEntityClass;
	}
}
