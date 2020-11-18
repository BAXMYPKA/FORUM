package ru.shop.forum.entities.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.shop.forum.entities.ForumMain;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ForumMainDto extends AbstractForumDto<ForumMain> {
	
	@EqualsAndHashCode.Include
	@NotBlank(message = "{field.notEmpty}")
	public String forumName;
}
