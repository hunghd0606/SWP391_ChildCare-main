package com.example.SWP_1631.config;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Role;
import com.example.SWP_1631.repository.AccountRepository;
import com.example.SWP_1631.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SessionService session;
    @Autowired
    private AccountRepository acc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = acc.getAccByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        } else {
            //        HttpSession session = null;
            session.set("VaiTro", user.getRole().getRoleName());
            session.set("acc", user);
//            System.out.printf(user.toString());

            List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
            GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
            grantList.add(authority);
        }


        return new MyUserDetails(user);
    }
}
