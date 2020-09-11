package ru.shop.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.shop.forum.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
