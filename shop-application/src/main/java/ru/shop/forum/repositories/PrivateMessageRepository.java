package ru.shop.forum.repositories;

import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends ForumEntityRepository<PrivateMessage> {
}
