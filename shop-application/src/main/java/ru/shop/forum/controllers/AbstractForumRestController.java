package ru.shop.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shop.controllers.AbstractRestController;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.entities.dto.AbstractForumDto;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.services.AbstractEntityService;


/**
 * {@link Pageable} URL parameters:
 * .param("page", "5")
 * .param("size", "10")
 * .param("sort", "id,desc")   // <-- no space after comma!
 * .param("sort", "name,asc")) // <-- no space after comma!
 *
 * @param <T>
 * @param <S>
 * @param <D>
 */
@Getter
//@Setter
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public abstract class AbstractForumRestController <
	T extends AbstractForumEntity,
	D extends AbstractForumDto<T>,
	S extends AbstractEntityService<T, ? extends ForumEntityRepository<T>>
	>
	extends AbstractRestController<T, D, S> {
	
	
	public AbstractForumRestController(S entityService, ModelMapper modelMapper, ObjectMapper objectMapper) {
		super(entityService, modelMapper, objectMapper);
	}
}
