package ru.shop.forum.entities;

import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts", schema = "FORUM")
public class Post extends AbstractEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@NotEmpty(message = "{field.notEmpty}")
	@Size(min = 7, max = 100, message = "{postName.length}")
	@Column(nullable = false, unique = true, length = 100)
	private String title;
	
	@NotEmpty(message = "{field.notEmpty}")
	@Size(min = 5, max = 1000, message = "{postText.length}")
	@Column(nullable = false, length = 1000)
	private String text;
	
	@ManyToOne( optional = false)
	@JoinColumn(name = "forum_section_id")
	private ForumSection forumSection;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
}
