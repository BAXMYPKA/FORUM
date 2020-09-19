package ru.shop.forum.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.AbstractEntity;

import java.util.List;

@Repository
public interface EntityRepository<T extends AbstractEntity, Long> extends JpaRepository<T, Long> {
}
