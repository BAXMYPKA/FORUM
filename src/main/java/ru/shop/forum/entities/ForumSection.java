package ru.shop.forum.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * The forum subject section.
 * If {{@link #getParent_section()}} is NULL - this is one of the root {@link ForumSection}
 */
@RequiredArgsConstructor
@Getter
@Setter
@Entity(name = "forum_section")
@Table(name = "Forum_Sections", schema = "FORUM")
public class ForumSection extends AbstractEntity {

	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_section_id", referencedColumnName = "id")
	private ForumSection parent_section;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent_section")
	private Set<ForumSection> subsections;
}
