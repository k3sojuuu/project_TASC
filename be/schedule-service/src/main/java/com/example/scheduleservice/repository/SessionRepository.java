package com.example.scheduleservice.repository;

import com.example.scheduleservice.model.Sessions;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Sessions, Long> {
    List<Sessions> getSessionsBySchedulesId(Long SchedulesId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sessions SET status_sessions = 2 WHERE id = :sessionId", nativeQuery = true)
    int updateStatusBySession(@Param("sessionId") Long sessionId);

    @Query(value = "SELECT COUNT(*) FROM sessions WHERE status_sessions = 2 and schedules_id = :scheduleId", nativeQuery = true)
    Long countSessionCompleted(@Param("scheduleId")Long scheduleId);

    @Transactional
    @Query(value = "INSERT INTO session_online (schedules_id, exercise, nutrition, status_sessions, session_no, description) " +
            "VALUES (:schedulesId, :exercise, :nutrition, :statusSessions, :sessionNo, :description)", nativeQuery = true)
    void CreateSessionOnline(@Param("schedulesId") Long schedulesId,
                             @Param("exercise") String exercise,
                             @Param("nutrition") String nutrition,
                             @Param("statusSessions") Long statusSessions,
                             @Param("sessionNo") Long sessionNo,
                             @Param("description") String description);
}
