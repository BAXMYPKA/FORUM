package ru.shop.forum.entities.dto;

import ru.shop.forum.entities.ForumSection;

public class ForumSectionDto extends AbstractForumDto<ForumSection> {
	
	public void setEntityClass(Class<ForumSection> forumSectionClass) {
		this.entityClass = forumSectionClass;
	}
}
