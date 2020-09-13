package ru.shop.forum.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Getter
@Setter
@Entity(name = "user_forum_settings")
@Table(name = "user_forum_settings", schema = "FORUM")
public class UserForumSettings extends AbstractEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Size(min = 10, max = 100, message = "{field.postsOnPage10-100}")
	@Column(name = "posts_on_page")
	private Integer postsOnPage = 10;
	
	@Column(name = "enable_private_messaging")
	private Boolean enablePrivateMessaging = true;
	
	@Column(name = "show_signature")
	private Boolean showSignature = true;
	
	@Column(name = "hide_birthdate")
	private Boolean hideBirthdate = true;
	
	@Pattern(message = "{field.timeZone}", regexp = "(?i)^\\w{1,25}/\\w{1,25}/*\\w*$")
	@Column
	private TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");
	
	@Size(max = 100, message = "{field.signatureMaxLength100}")
	@Column(length = 100)
	private String signature;
	
	@OneToOne(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}
