package ru.shop.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.AbstractEntity;

@Repository
public interface EntityRepository<T extends AbstractEntity, ID> extends JpaRepository<T, Long> {

}
