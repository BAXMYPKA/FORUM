package ru.shop.forum.entities;

import lombok.*;
import ru.shop.entities.User;
import ru.shop.entities.utils.UniqueNickname;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Forum_Users", schema = "FORUM")
public class ForumUser extends User {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@UniqueNickname(message = "{user.nonUniqueNickname}")
	@NonNull
	@NotEmpty(message = "{field.notEmpty}")
	@Column(updatable = false, nullable = false, unique = true)
	private String nickName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avatar_id")
	private ImgAvatar avatar;
	
	@OneToMany(mappedBy = "updatedBy")
	private Set<Post> postsUpdatedBy;
	
	@OneToOne(optional = false, orphanRemoval = true, mappedBy = "forumUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ForumUserSettings userForumSettings = new ForumUserSettings();
	
	@OneToMany(mappedBy = "fromForumUser")
	private Set<PrivateMessage> privateMessagesFrom;
	
	@ManyToMany(mappedBy = "toForumUsers")
	private Set<PrivateMessage> privateMessagesTo;
	
}
