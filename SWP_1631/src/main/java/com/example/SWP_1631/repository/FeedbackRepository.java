package com.example.SWP_1631.repository;

import com.example.SWP_1631.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    @Query("SELECT u FROM Feedback u WHERE  u.fbDate =:dateFormat")
    Feedback getFeedbackByDate(@Param("dateFormat") Date dateFormat);
}
