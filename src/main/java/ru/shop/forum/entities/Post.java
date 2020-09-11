package ru.shop.forum.entities;

import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts", schema = "FORUM")
public class Post {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "IdGenerator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "IdGenerator", sequenceName = "post_id", schema = "FORUM", initialValue = 1000)
	private Long id;
	
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Size(min = 7, max = 100, message = "{postName.length}")
	@Column(nullable = false, unique = true, length = 100)
	private String name;
	
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Size(min = 5, max = 1000, message = "{postText.length}")
	@Column(nullable = false, length = 1000)
	private String text;
	
	@PastOrPresent(message = "{date.pastOrPresent}")
	@Column
	private LocalDateTime created;
	
	@PastOrPresent(message = "{date.pastOrPresent}")
	@Column
	private LocalDateTime updated;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "updated_by_user_id")
	private User updatedBy;
	
	@NonNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "forum_section_id")
	private ForumSection forumSection;
	
	@NonNull
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	@PrePersist
	public void prePersist() {
		this.created = LocalDateTime.now();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.updated = LocalDateTime.now();
//		SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
