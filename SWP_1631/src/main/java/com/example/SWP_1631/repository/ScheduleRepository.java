package com.example.SWP_1631.repository;

import com.example.SWP_1631.entity.Account;
import com.example.SWP_1631.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {


    @Query("SELECT u FROM Schedule u WHERE u.clazzId.clazzId =:classID AND u.scheduleDate =:newDate AND u.slotId.slotId =:slot_id ")
    public Schedule getScheduleByClassDateSlot(@Param("classID") int classID, @Param("newDate") String newDate, @Param("slot_id") int slot_id);
}
