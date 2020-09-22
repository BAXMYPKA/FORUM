package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ForumSection;

public class ForumSectionDto extends AbstractForumDto<ForumSection> {
	
	@Override
	protected void setEntityClass(Class<ForumSection> forumSectionClass) {
		this.entityClass = forumSectionClass;
	}
}
