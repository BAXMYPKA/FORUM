package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.Post;

public class PostDto extends AbstractForumDto<Post> {
	@Override
	protected void setAbstractEntityClass(Class<Post> abstractEntityClass) {
		this.abstractEntityClass = abstractEntityClass;
	}
}
