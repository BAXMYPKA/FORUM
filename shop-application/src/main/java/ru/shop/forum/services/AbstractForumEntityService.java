package ru.shop.forum.services;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.forum.repositories.ForumEntityRepository;
import ru.shop.services.AbstractEntityService;

@Getter
@Setter
@Service
public abstract class AbstractForumEntityService <T extends AbstractForumEntity, R extends ForumEntityRepository<T>>
	extends AbstractEntityService<T, R> {
	
	public AbstractForumEntityService(R repository) {
		super(repository);
	}
}
