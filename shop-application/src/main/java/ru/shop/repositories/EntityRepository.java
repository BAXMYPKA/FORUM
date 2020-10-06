package ru.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shop.entities.AbstractEntity;

import java.util.Collection;

@Repository
public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
	
}

