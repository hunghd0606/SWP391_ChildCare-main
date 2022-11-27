package com.example.SWP_1631.service;

import com.example.SWP_1631.entity.Slot;

import java.util.Optional;

public interface SlotService {
    public Optional<Slot> getSlotById(Integer id);
}
