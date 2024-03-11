package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.GroupDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupDao, Long> {
    @Query(nativeQuery = true, value = "select * from groupLearn where created_by = :userId")
    List<GroupDao> getListGroupByUserCreated(Long userId);
    @Query(nativeQuery = true, value = "select group_name from groupLearn where created_by = :userId")
    List<String> getListGroupName(Long userId);
    @Query(nativeQuery = true, value = "select * from groupLearn where id = :groupId")
    GroupDao getGroupDaoByGroupId(Long groupId);
}
