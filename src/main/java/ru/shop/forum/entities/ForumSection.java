package ru.shop.forum.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * The forum subject section.
 * If {{@link #getParent_section()}} is NULL - this is one of the root {@link ForumSection}
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity(name = "forum_section")
@Table(name = "Forum_Sections", schema = "FORUM")
public class ForumSection {
	
	@Transient
	protected static final long SerialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "IdGenerator", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "IdGenerator", sequenceName = "section_id", schema = "FORUM", initialValue = 1000)
	private Long id;
	
	@NonNull
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
