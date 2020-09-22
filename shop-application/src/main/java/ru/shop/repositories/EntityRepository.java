package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.entities.AbstractEntity;

@Repository
public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
