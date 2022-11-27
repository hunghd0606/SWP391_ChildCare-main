package com.example.SWP_1631.service;

import java.util.List;
import java.util.Optional;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Kindergartner;


public interface KindergartnerService {

    List<Kindergartner> getListKinder();

    List<Kindergartner> getListKinderByIdParent(Integer id);

    void delete(Integer KinderId);


    void update(Kindergartner kd);


    void save(Kindergartner kinder);

    Optional<Kindergartner> getKindergartnerById(Integer id);

}
