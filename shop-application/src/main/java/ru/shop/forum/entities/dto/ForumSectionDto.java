package ru.shop.forum.entities.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.shop.forum.entities.ForumSection;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ForumSectionDto extends AbstractForumDto<ForumSection> {
	
//	public void setEntityClass(Class<ForumSection> forumSectionClass) {
//		this.entityClass = forumSectionClass;
//	}
}
