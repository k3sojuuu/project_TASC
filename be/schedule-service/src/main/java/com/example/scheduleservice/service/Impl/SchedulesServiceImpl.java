package com.example.scheduleservice.service.Impl;

import com.example.scheduleservice.dao.SchedulesDao;
import com.example.scheduleservice.model.DTO.*;
import com.example.scheduleservice.model.Exercise;
import com.example.scheduleservice.model.Response.MyResponse;
import com.example.scheduleservice.model.Schedules;
import com.example.scheduleservice.model.Sessions;
import com.example.scheduleservice.repository.ExerciseRepository;
import com.example.scheduleservice.repository.SchedulesRepository;
import com.example.scheduleservice.repository.SessionRepository;
import com.example.scheduleservice.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulesServiceImpl implements SchedulesService {
    @Autowired
    private SchedulesDao schedulesDao;
    @Autowired
    private SchedulesRepository schedulesRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    public ResponseEntity<?> sessionOffline(SessionOfflineDTO sessions) {
        return ResponseEntity.ok(schedulesDao.setSessionOffline(sessions));
    }

    @Override
    public ResponseEntity<?> createSessionOnline(SessionOnlineDTO sessions) {
        Sessions ses = Sessions.builder()
                .schedulesId(sessions.getSchedulesId())
                .session_no(sessions.getSessionNo())
                .statusSessions(1L)
                .description(sessions.getDescription())
                .nutrition(sessions.getNutrition())
                .sessionName(sessions.getSessionName())
                .build();
        ses = sessionRepository.save(ses);
        Long key = ses.getId();
        if (key !=null){
            return ResponseEntity.ok("create ss");
        }else{
            return ResponseEntity.status(404).body("fail");
        }
    }

    @Override
    public ResponseEntity<?> setExercise(Exercise exercise) {
        Exercise exe = new Exercise();
        exe.setSessionId(exercise.getSessionId());
        exe.setExerciseName(exercise.getExerciseName());
        exe.setExeSet(exercise.getExeSet());
        exe.setExeRep(exercise.getExeRep());
        exe.setDescriptions(exercise.getDescriptions());
        Long result = schedulesDao.setExercise(exe);
        if (result != null){
            return ResponseEntity.ok("Add Sussess");
        }else {
            return ResponseEntity.status(400).body("add Failer");
        }
    }

    @Override
    public ResponseEntity<?> getScheduleByUserId(Long userId) {
        List<Schedules> listSchedules = schedulesRepository.getSchedulesByUid(userId);
        GetSchedulesDTO schedulesDTO = new GetSchedulesDTO();
        schedulesDTO.setUserId(userId);
        schedulesDTO.setSchedulesList(listSchedules);
        if (listSchedules != null){
            return ResponseEntity.ok(schedulesDTO);
        }else {
            return ResponseEntity.status(404).body("not found");
        }

    }

    @Override
    public ResponseEntity<?> getSessionBySchedule(Long scheduleId) {
        List<Sessions> sessionsList = sessionRepository.getSessionsBySchedulesId(scheduleId);
        GetSessionDTO sessionDTO = new GetSessionDTO();
        sessionDTO.setSchedulesId(scheduleId);
        sessionDTO.setSessionsList(sessionsList);
        if (sessionsList != null){
            return ResponseEntity.ok(sessionDTO);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }

    @Override
    public ResponseEntity<?> getExerciseBySession(Long sessionId) {
        List<Exercise> exercises = exerciseRepository.getExercisesBySessionId(sessionId);
        GetExerciserDTO exerciserDTO = new GetExerciserDTO();
        exerciserDTO.setSessionId(sessionId);
        exerciserDTO.setExerciseList(exercises);
        if (exercises != null){
            return ResponseEntity.ok(exerciserDTO);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }

    @Override
    public ResponseEntity<?> setExerciseByUser(Long exerciseId) {
        int key = exerciseRepository.updateStatusByExerciseId(exerciseId);
        if (key > 0){
            return ResponseEntity.ok("update ss");
        }else {
            return ResponseEntity.status(404).body("update failse");
        }
    }

    @Override
    public ResponseEntity<?> setSessionByUser(Long sessionId) {
        MyResponse myResponse = new MyResponse();
        int key = sessionRepository.updateStatusBySession(sessionId);
        if (key > 0){
            myResponse.setMessage("update ss");
            return ResponseEntity.ok(myResponse);
        }else {
            myResponse.setMessage("update failse");
            return ResponseEntity.status(404).body(myResponse);
        }
    }

    @Override
    public ResponseEntity<?> setScheduleByUser(Long scheduleId) {
        int key = schedulesRepository.updateStatusBySchedules(scheduleId);
        if (key > 0){
            return ResponseEntity.ok("update ss");
        }else {
            return ResponseEntity.status(404).body("update failse");
        }
    }

    @Override
    public ResponseEntity<?> CountExerciseComplete() {
        Long result = exerciseRepository.countExercisesCompleted();
        if (result > 0){
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }

    @Override
    public ResponseEntity<?> CountSessionComplete(Long scheduleId) {
        Long result = sessionRepository.countSessionCompleted(scheduleId);
        if (result > 0){
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }

    @Override
    public ResponseEntity<?> CountScheduleComplete() {
        Long result = schedulesRepository.countScheduleCompleted();
        if (result > 0){
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }

    @Override
    public ResponseEntity<?> sessionTotal(Long scheduleId,Long userId) {
        Long result = schedulesRepository.getTotalSessionByScheduleIdAndUserId(userId,scheduleId);
        if (result > 0){
            return ResponseEntity.ok(result);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }
}
