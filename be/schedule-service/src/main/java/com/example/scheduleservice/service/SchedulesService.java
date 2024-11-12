package com.example.scheduleservice.service;

import com.example.scheduleservice.model.DTO.SessionOfflineDTO;
import com.example.scheduleservice.model.DTO.SessionOnlineDTO;
import com.example.scheduleservice.model.Exercise;
import com.example.scheduleservice.model.Schedules;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface SchedulesService {
    ResponseEntity<?> sessionOffline(SessionOfflineDTO sessions);
    ResponseEntity<?> createSessionOnline(SessionOnlineDTO sessions);
    ResponseEntity<?> setExercise(Exercise exercise);
    ResponseEntity<?> getScheduleByUserId(Long UserId);
    ResponseEntity<?> getSessionBySchedule(Long ScheduleId);
    ResponseEntity<?> getExerciseBySession(Long SessionId);
    ResponseEntity<?> setExerciseByUser(Long exerciseId);
    ResponseEntity<?> setSessionByUser(Long sessionId);
    ResponseEntity<?> setScheduleByUser(Long scheduleId);
    ResponseEntity<?> CountExerciseComplete();
    ResponseEntity<?> CountSessionComplete(Long scheduleId);
    ResponseEntity<?> CountScheduleComplete();

    ResponseEntity<?> sessionTotal(Long scheduleId,Long userId);
}
