package ru.shop.forum.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Users", schema = "FORUM")
public class User extends AbstractEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "IdGenerator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "IdGenerator", sequenceName = "user_id", schema = "FORUM", initialValue = 1000)
	private Long id;
	
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Email(message = "{email.notValid}")
	@Column(unique = true, nullable = false)
	private String email;
	
	@NonNull
	@Column(nullable = false)
	private String firstName;
	
	@Column
	private String lastName;
	
	@Past(message = "{date.past}")
	private LocalDate birthdate;
	
	@Column
	@Lob
	@Size(max = 512000, message = "{photo.maxSize}")
	private byte[] photo;
}
