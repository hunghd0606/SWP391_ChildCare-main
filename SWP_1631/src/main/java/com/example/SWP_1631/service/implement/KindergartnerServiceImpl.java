package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Kindergartner;
import com.example.SWP_1631.repository.KindergartnerRepository;
import com.example.SWP_1631.service.KindergartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KindergartnerServiceImpl implements KindergartnerService {
    @Autowired
    private KindergartnerRepository kinderRes;

    public List<Kindergartner> getListKinder() {
        return kinderRes.findAll();
    }

    @Override
    public List<Kindergartner> getListKinderByIdParent(Integer id) {
        return kinderRes.getKindergartnerByIdParentId(id);
    }

    @Override
    public void delete(Integer KinderId) {
        Optional<Kindergartner> kinOp = kinderRes.findById(KinderId);
        kinOp.ifPresent(u -> kinderRes.delete(u));
    }

    @Override
    public void update(Kindergartner kd) {
        kinderRes.save(kd);
    }

    @Override
    public void save(Kindergartner kinder) {
        kinderRes.save(kinder);
    }

    @Override
    public Optional<Kindergartner> getKindergartnerById(Integer id) {
        Optional<Kindergartner> acc = kinderRes.findById(id);
        return acc;
    }
}
