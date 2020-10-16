package ru.shop.entities.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shop.entities.AbstractEntity;
import ru.shop.entities.utils.ShopViews;
import ru.shop.entities.utils.ValidationCreateGroup;
import ru.shop.entities.utils.ValidationUpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public abstract class AbstractDto<T extends AbstractEntity> {
	
	protected Class<T> entityClass;
	
	@JsonView(ShopViews.ExternalView.class)
	@Null(groups = {ValidationCreateGroup.class}, message = "{field.mustBeNull}")
	@NotNull(groups = {ValidationUpdateGroup.class}, message = "{field.notEmpty}")
	private Long id;
	
}
