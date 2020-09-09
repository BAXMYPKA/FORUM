package ru.shop.forum.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users")
public class User extends AbstractEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "IdGenerator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "IdGenerator", sequenceName = "user_id", initialValue = 1000)
	private long id;
	
	@Column(unique = true)
	private String email;
}
