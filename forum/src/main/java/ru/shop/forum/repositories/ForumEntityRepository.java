package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.AbstractForumEntity;

@Repository
public interface ForumEntityRepository<AbstractForumEntity> extends EntityRepository<AbstractForumEntity, Long> {
}
