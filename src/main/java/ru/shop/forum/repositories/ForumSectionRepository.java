package ru.shop.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.ForumSection;

@Repository
public interface ForumSectionRepository extends JpaRepository<ForumSection, Long> {

}
