package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Role;
import com.example.SWP_1631.repository.RoleRepository;
import com.example.SWP_1631.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRes;

    @Override
    public List<Role> getAllRole() {
        return roleRes.findAll();
    }
}
