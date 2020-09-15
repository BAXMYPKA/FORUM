package ru.shop.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@MappedSuperclass
public class AbstractShopEntity extends AbstractEntity {
	
//	@Version
//	private Long version;
	
//	@EqualsAndHashCode.Include
//	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
//	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
//	@Id
//	@Column(updatable = false, nullable = false)
//	@GeneratedValue(generator = "ShopIdGenerator", strategy = GenerationType.SEQUENCE)
//	@SequenceGenerator(name = "ShopIdGenerator", sequenceName = "entity_id", schema = "SHOP", initialValue = 1000)
//	private Long id;
	
}
