package ru.shop.forum.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The forum's subjects sections. Can contain other {@link ForumSection}s or {@link Post}s if {@link #postsContainer} is true;
 * If {{@link #parentSection}} is NULL - this is one of the root {@link ForumSection}
 */
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Forum_Sections", schema = "FORUM")
public class ForumSection extends AbstractForumEntity {

	@Transient
	protected static final long SerialVersionUID = 1L;
	
	//TODO: unique
	@NotBlank(message = "{field.notEmpty}")
	@Size(min = 3, max = 100, message = "{field.length3-100}")
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column
	private String description;
	
	/**
	 * Whether it can contain {@link Post}s or serves only as the subject section.
	 */
	@Column
	private Boolean postsContainer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_section_id", referencedColumnName = "id")
	private ForumSection parentSection;

	@OneToMany(mappedBy = "parentSection")
	private Set<ForumSection> childSections;
}
