package com.example.scheduleservice.repository;

import com.example.scheduleservice.model.Exercise;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    List<Exercise> getExercisesBySessionId(Long sessionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE exercise SET status = 2 WHERE id = :exerciseId", nativeQuery = true)
    int updateStatusByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query(value = "SELECT COUNT(*) FROM exercise WHERE status = 2", nativeQuery = true)
    Long countExercisesCompleted();
}
