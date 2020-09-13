package ru.shop.forum.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
	
	@NotBlank(message = "{field.notEmpty}")
	@Column(unique = true, nullable = false)
	private String url;
	
	@ManyToOne( optional = false)
	@JoinColumn(name = "forum_section_id")
	private ForumSection forumSection;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
}
