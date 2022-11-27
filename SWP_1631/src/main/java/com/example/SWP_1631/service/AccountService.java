package com.example.SWP_1631.service;


import com.example.SWP_1631.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AccountService extends UserDetailsService {
    public List<Account> getListAccount();

    void delete(Integer id);

    public Optional<Account> getAccount(Integer id);


    public void update(Account user);

    public boolean delete(long id);

    void save(Account user);


    List<Account> getListAccountByIdRole(int id);

    List<Account> getListAccountTeacherNotClass();

    boolean checkEmailExitInDatabase(String email);

    Account getAccByEmail(String email);
}
