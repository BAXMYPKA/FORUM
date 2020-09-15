package ru.shop.entities;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.shop.forum.entities.User;
import ru.shop.forum.entities.utils.ValidationCreateGroup;
import ru.shop.forum.entities.utils.ValidationUpdateGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Customers", schema = "SHOP")
public class Customer extends User {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Null(groups = ValidationCreateGroup.class, message = "{field.mustBeNull}")
	@NotNull(groups = ValidationUpdateGroup.class, message = "{field.notEmpty}")
	@Id
	@Column(updatable = false, nullable = false)
	@GeneratedValue(generator = "ShopIdGenerator", strategy = GenerationType.SEQUENCE)
	private Long id;
	
//	private Address address;

//	private Set<Phone> phones;
}
