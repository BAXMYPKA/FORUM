package ru.shop.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
