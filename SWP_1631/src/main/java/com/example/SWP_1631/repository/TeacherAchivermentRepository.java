package com.example.SWP_1631.repository;

import com.example.SWP_1631.entity.TeacherAchiverment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherAchivermentRepository extends JpaRepository<TeacherAchiverment, Integer> {
}
