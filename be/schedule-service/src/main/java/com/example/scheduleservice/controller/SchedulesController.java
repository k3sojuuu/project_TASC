package com.example.scheduleservice.controller;

import com.example.scheduleservice.model.DTO.SessionOfflineDTO;
import com.example.scheduleservice.model.DTO.SessionOnlineDTO;
import com.example.scheduleservice.model.Exercise;
import com.example.scheduleservice.model.Schedules;
import com.example.scheduleservice.model.Sessions;
import com.example.scheduleservice.service.Impl.SchedulesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sv4/schedules")
public class SchedulesController {
    @Autowired
    private SchedulesServiceImpl schedulesService;

    @PostMapping("/setSessionOnline")
    public ResponseEntity<?> setSessionsOnline(@RequestBody SessionOnlineDTO sessions){
        return schedulesService.createSessionOnline(sessions);
    }//ok
    @PostMapping("/setExercise")
    public ResponseEntity<?> setExercise(@RequestBody Exercise exerciseSession){
        return schedulesService.setExercise(exerciseSession);
    }//ok
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getScheduleById")
    public ResponseEntity<?> getScheduleByUserId(@RequestParam Long userId){
        return schedulesService.getScheduleByUserId(userId);
    }//ok
    @GetMapping("/getSessionByScheduleId")
    public ResponseEntity<?> getSessionBySchedule(@RequestParam Long scheduleId){
        return schedulesService.getSessionBySchedule(scheduleId);
    }//ok
    @GetMapping("/getExerciseBySessionId")
    public ResponseEntity<?> getExerciseBySession(@RequestParam Long sessionId){
        return schedulesService.getExerciseBySession(sessionId);
    }//ok
    @PutMapping("/setCompleteExercise")
    public ResponseEntity<?> setComplateExercise(@RequestParam Long exerciseId){
        return schedulesService.setExerciseByUser(exerciseId);
    }//ok

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/setCompleteSession")
    public ResponseEntity<?> setCompleteSession(@RequestParam Long sessionId){
        return schedulesService.setSessionByUser(sessionId);
    }//ok
    @PutMapping("/setCompleteSchedule")
    public ResponseEntity<?>setCompleteSchedule(@RequestParam Long scheduleId){
        return schedulesService.setScheduleByUser(scheduleId);
    }//ok
    @GetMapping("/getCountExerciseComplete")
    public ResponseEntity<?> getCountExerciseComplete(){
        return schedulesService.CountExerciseComplete();
    }//ok
    @GetMapping("/getCountSessionComplete")
    public ResponseEntity<?> getCountSessionCompleteByScheduleId(@RequestParam Long scheduleId){
        return schedulesService.CountSessionComplete(scheduleId);
    }//ok
    @GetMapping("/getCountScheduleComplete")
    public ResponseEntity<?> getCountScheduleComplete(){
        return schedulesService.CountScheduleComplete();
    }//ok
    @GetMapping("getTotalSessionByScheduleIdAndUserId")
    public ResponseEntity<?> getTotalSession(@RequestParam Long scheduleId,
                                             @RequestParam Long userId){
        return schedulesService.sessionTotal(scheduleId,userId);
    }
    @PostMapping("/createSchedule")
    public ResponseEntity<?> createSchedule(@RequestBody Schedules schedules){
        return null;
    } //not ok

    @PostMapping("/setSessionOffline")
    public ResponseEntity<?> setSessionsOffline(@RequestBody SessionOfflineDTO sessions){
        return schedulesService.sessionOffline(sessions);
    } //not ok
}
