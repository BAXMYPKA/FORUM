package ru.shop.forum.entities.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AbstractForumDto<T extends AbstractForumEntity> extends AbstractDto<T> {
	
	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
	private Long id;
	
}
