package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.repository.AccountRepository;
import com.example.SWP_1631.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accRes;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public void save(Account user) {
        accRes.save(user);
    }

    @Override
    public List<Account> getListAccountByIdRole(int id) {
        return accRes.getAllAccountByRoldId(id);
    }

    @Override
    public List<Account> getListAccountTeacherNotClass() {
        return accRes.getAllAccountByTeacher();
    }

    @Override
    public boolean checkEmailExitInDatabase(String email) {
        Account account = accRes.getAccByEmail(email);
        if (account == null) {
            return false;
        }
        return true;
    }

    @Override
    public Account getAccByEmail(String email) {
        return accRes.getAccByEmail(email);
    }

    @Override
    public List<Account> getListAccount() {
        return accRes.findAll();
    }

    @Override
    public void update(Account user) {
        accRes.save(user);
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public void delete(Integer id) {
        accRes.deleteById(id);
    }

    @Override
    public Optional<Account> getAccount(Integer id) {
        Optional<Account> acc = accRes.findById(id);

        return acc;
    }
}
