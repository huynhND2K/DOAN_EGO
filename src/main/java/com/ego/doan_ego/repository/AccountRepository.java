package com.ego.doan_ego.repository;

import com.ego.doan_ego.entities.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<AccountDao, Long> {
    @Query(nativeQuery = true, value = "select * from account where username = :username")
    AccountDao getAccountByUsername(String username);


}
