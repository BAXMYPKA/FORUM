package ru.shop.forum.entities.dto;

import lombok.*;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class AbstractDto<T extends AbstractEntity> {
	
	protected Class<T> abstractEntityClass;
	
	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
	private Long id;
	
	protected abstract void setAbstractEntityClass(Class<T> abstractEntityClass);
}
