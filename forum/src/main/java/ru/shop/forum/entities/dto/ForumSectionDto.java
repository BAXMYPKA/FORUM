package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ForumSection;

public class ForumSectionDto extends AbstractForumDto<ForumSection> {
	
	@Override
	protected void setAbstractEntityClass(Class<ForumSection> forumSectionClass) {
		this.abstractEntityClass = forumSectionClass;
	}
}
