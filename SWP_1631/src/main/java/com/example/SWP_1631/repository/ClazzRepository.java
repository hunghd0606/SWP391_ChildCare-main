package com.example.SWP_1631.repository;

import com.example.SWP_1631.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClazzRepository extends JpaRepository<Clazz, Integer> {
    @Query("SELECT  c FROM Clazz c WHERE c.account.accountId =:accountId ")
    Clazz getClazzByIdAccount(@Param("accountId") int accountId);
}
