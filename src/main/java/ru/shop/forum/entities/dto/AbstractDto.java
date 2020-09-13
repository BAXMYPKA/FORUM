package ru.shop.forum.entities.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shop.forum.entities.AbstractEntity;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class AbstractDto<T extends AbstractEntity> implements Serializable {
	
	@EqualsAndHashCode.Include
	private Long id;
	
}
