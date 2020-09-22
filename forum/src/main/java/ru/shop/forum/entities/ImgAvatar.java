package ru.shop.forum.entities;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "img_avatars", schema = "FORUM")
public class ImgAvatar extends AbstractForumEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;

	@URL
	@Column(unique = true, nullable = false)
	private String url;
	
	@Column
	@Lob
	@Size(max = 512000, message = "{avatar.maxSize}")
	private byte[] img;
	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "avatar", cascade = CascadeType.ALL)
	private ForumUser forumUser;
}
