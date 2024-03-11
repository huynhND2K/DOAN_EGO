package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserDao, Long> {
    @Query(nativeQuery = true, value = "select * from users where id = :userId")
    UserDao getUserDaoById(Long userId);
}
