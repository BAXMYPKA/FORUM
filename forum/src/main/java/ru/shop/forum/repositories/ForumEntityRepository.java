package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.AbstractEntity;
import ru.shop.forum.entities.AbstractForumEntity;

@Repository
public interface ForumEntityRepository<T extends AbstractForumEntity> extends EntityRepository<T, Long> {
}
