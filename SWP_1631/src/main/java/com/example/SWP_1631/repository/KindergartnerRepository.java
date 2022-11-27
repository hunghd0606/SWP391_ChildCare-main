package com.example.SWP_1631.repository;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Kindergartner;
import com.example.SWP_1631.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindergartnerRepository extends JpaRepository<Kindergartner, Integer> {


    @Query("SELECT k FROM Kindergartner k WHERE k.account.accountId = :id")
    public List<Kindergartner> getKindergartnerByIdParentId(@Param("id") Integer id);

}
