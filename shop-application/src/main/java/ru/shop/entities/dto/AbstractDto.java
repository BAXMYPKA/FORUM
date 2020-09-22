package ru.shop.entities.dto;

import lombok.*;
import ru.shop.entities.AbstractEntity;
import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class AbstractDto<T extends AbstractEntity> {
	
	protected Class<T> entityClass;
	
	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
	private Long id;
	
	protected abstract void setEntityClass(Class<T> entityClass);
}
