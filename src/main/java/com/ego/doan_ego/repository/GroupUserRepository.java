package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.GroupUserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUserDao, Long> {
    @Query(nativeQuery = true, value = "select * from userGroup where group_id = :groupId")
    List<GroupUserDao> getGroupUserDaoByGroupId(Long groupId);

}
