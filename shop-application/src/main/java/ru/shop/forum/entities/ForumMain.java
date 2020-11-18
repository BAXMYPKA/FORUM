package ru.shop.forum.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Forum_Main", schema = "FORUM")
public class ForumMain extends AbstractForumEntity {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Column(name = "forum_name", nullable = false)
	public String forumName;
}
