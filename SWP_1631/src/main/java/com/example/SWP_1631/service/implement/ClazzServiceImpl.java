package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Clazz;
import com.example.SWP_1631.repository.ClazzRepository;
import com.example.SWP_1631.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzRepository classRes;

    @Override
    public List<Clazz> getAllClazz() {
        return classRes.findAll();
    }

    @Override
    public Optional<Clazz> getById(int i) {
        Optional<Clazz> clazz = classRes.findById(i);
        return clazz;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Clazz> clazz = classRes.findById(id);
        if (clazz.isPresent()) {
            classRes.delete(clazz.get());
        }
    }

    @Override
    public void save(Clazz cal) {
        classRes.save(cal);
    }

    @Override
    public Clazz getClazzByIdAccount(int accountId) {
        return classRes.getClazzByIdAccount(accountId);
    }
}
