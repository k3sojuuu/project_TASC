package com.example.scheduleservice.repository;

import com.example.scheduleservice.model.DTO.GetSchedulesDTO;
import com.example.scheduleservice.model.Schedules;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules,Long> {
    List<Schedules> getSchedulesByUid(Long userId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE schedules SET status_schedules = 2, end_at = CURRENT_TIMESTAMP WHERE id = :schedulesId", nativeQuery = true)
    int updateStatusBySchedules(@Param("schedulesId") Long schedulesId);

    @Query(value = "select session_number from schedules where user_id=:userId and id=:scheduleId",nativeQuery = true)
    Long getTotalSessionByScheduleIdAndUserId(@Param("userId")Long userId,
                                              @Param("scheduleId")Long scheduleId);
    @Query(value = "SELECT COUNT(*) FROM schedules WHERE status_schedules = 2", nativeQuery = true)
    Long countScheduleCompleted();
}
