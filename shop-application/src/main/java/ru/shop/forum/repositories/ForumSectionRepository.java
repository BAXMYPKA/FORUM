package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.ForumSection;

@Repository
public interface ForumSectionRepository extends ForumEntityRepository<ForumSection> {
}
