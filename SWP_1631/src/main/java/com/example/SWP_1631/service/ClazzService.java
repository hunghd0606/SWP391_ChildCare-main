package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Clazz;

import java.util.List;
import java.util.Optional;

public interface ClazzService {
    public List<Clazz> getAllClazz();


    Optional<Clazz> getById(int i);

    public void deleteById(Integer id);


    void save(Clazz cal);

    Clazz getClazzByIdAccount(int accountId);
}
