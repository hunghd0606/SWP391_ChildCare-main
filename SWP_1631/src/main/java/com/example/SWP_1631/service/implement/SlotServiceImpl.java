package com.example.SWP_1631.service.implement;

import com.example.SWP_1631.entity.Slot;
import com.example.SWP_1631.repository.SlotRepository;
import com.example.SWP_1631.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlotServiceImpl implements SlotService {
    @Autowired
    private SlotRepository slotRepository;

    @Override
    public Optional<Slot> getSlotById(Integer id) {
        return slotRepository.findById(id);
    }
}
