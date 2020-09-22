package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.AbstractForumEntity;
import ru.shop.repositories.EntityRepository;

@Repository
public interface ForumEntityRepository<T extends AbstractForumEntity> extends EntityRepository<T> {
}
