package ru.shop.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.AbstractForumEntity;

@Repository
public interface EntityRepository<T extends AbstractForumEntity, Long> extends JpaRepository<T, Long> {

}
