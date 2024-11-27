package com.example.scheduleservice.service.Impl;

import com.example.scheduleservice.constan.SchedulesStatus;
import com.example.scheduleservice.constan.SchedulesType;
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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SchedulesServiceImpl implements SchedulesService {
    @Autowired
    private SchedulesDao schedulesDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SchedulesRepository schedulesRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    private static final String EXERCISE_API_URL = "http://localhost:8004/uploadVideos/upload-video";
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

    private String uploadVideoToImgur(MultipartFile video) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "multipart/form-data");
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("videos", new MultipartFileResource(video));
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    EXERCISE_API_URL, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String videoUrl = response.getBody();
                return videoUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> setExercise(Exercise exercise, MultipartFile videos) {
        String videoUrl = uploadVideoToImgur(videos);
        Exercise exe = new Exercise();
        exe.setSessionId(exercise.getSessionId());
        exe.setExerciseName(exercise.getExerciseName());
        exe.setExeSet(exercise.getExeSet());
        exe.setExeRep(exercise.getExeRep());
        exe.setDescriptions(exercise.getDescriptions());
        exe.setVideoPath(videoUrl);
        Long result = schedulesDao.setExercise(exe);
        if (result != null){
            return ResponseEntity.ok(videoUrl);
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
        MyResponse myResponse = new MyResponse();
        int key = exerciseRepository.updateStatusByExerciseId(exerciseId);
        if (key > 0){
            myResponse.setMessage("update ss");
            return ResponseEntity.ok(myResponse);
        }else {
            myResponse.setMessage("update fail");
            return ResponseEntity.status(404).body(myResponse);
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
    public ResponseEntity<?> CountExerciseComplete(Long sessionId) {
        Long result = exerciseRepository.countExercisesCompleted(sessionId);
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
    public MyResponse CreateSchedules(Schedules schedules) {
        MyResponse myResponse = new MyResponse();
        try {
            Schedules create = Schedules.builder()
                    .uid(schedules.getUid())
                    .ptId(schedules.getPtId())
                    .startAt(new Date())
                    .sessionNumber(schedules.getSessionNumber())
                    .schedulesType(SchedulesType.ONLINE)
                    .statusSchedules(SchedulesStatus.TRAINING)
                    .price(schedules.getPrice())
                    .courseId(schedules.getCourseId())
                    .build();
            schedulesRepository.save(create);
            Long key = create.getId();
            if (key != null) {
                myResponse.setMessage("Add success");
                myResponse.setStatus(200);
                myResponse.setData(create);
            } else {
                myResponse.setMessage("Add fail");
                myResponse.setStatus(400);
                myResponse.setData(null);
            }
        } catch (Exception e) {
            myResponse.setMessage("An error occurred: " + e.getMessage());
            myResponse.setStatus(500);
            myResponse.setData(null);
            System.err.println("Error while creating schedule: " + e.getMessage());
        }
        return myResponse;
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
