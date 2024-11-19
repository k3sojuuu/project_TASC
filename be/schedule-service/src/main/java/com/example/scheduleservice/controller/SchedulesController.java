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
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> setExercise(@RequestParam("sessionId") Long sessionId,
                                         @RequestParam("exerciseName") String exerciseName,
                                         @RequestParam("exeSet") Long exeSet,
                                         @RequestParam("exeRep") Long exeRep,
                                         @RequestParam("descriptions") String descriptions,
                                         @RequestParam("status") Long status,
                                         @RequestParam("videos") MultipartFile videos) {
        // Tạo đối tượng Exercise từ các tham số
        Exercise exerciseSession = new Exercise();
        exerciseSession.setSessionId(sessionId);
        exerciseSession.setExerciseName(exerciseName);
        exerciseSession.setExeSet(exeSet);
        exerciseSession.setExeRep(exeRep);
        exerciseSession.setDescriptions(descriptions);
        exerciseSession.setStatus(status);

        // Gọi service để xử lý video và lưu exercise
        return schedulesService.setExercise(exerciseSession, videos);
    }
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
    @CrossOrigin(origins = "http://localhost:4200")
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
    @GetMapping("/getCountSessionComplete")
    public ResponseEntity<?> getCountSessionCompleteByScheduleId(@RequestParam Long scheduleId){
        return schedulesService.CountSessionComplete(scheduleId);
    }//ok
    @GetMapping("/getCountExerciseComplete")
    public ResponseEntity<?> getCountExerciseCompleteBySessionId(@RequestParam Long sessionId){
        return schedulesService.CountExerciseComplete(sessionId);
    }

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
