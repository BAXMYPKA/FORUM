package ru.shop.forum.entities.dto;

import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;
import ru.shop.forum.entities.Post;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class PostDto extends AbstractForumDto<Post> {
	
	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
	private Long id;

	public void setEntityClass(Class<Post> entityClass) {
		this.entityClass = entityClass;
	}
}
