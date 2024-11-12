package com.example.scheduleservice.model.DTO;

import com.example.scheduleservice.model.Exercise;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessionOfflineDTO {
    private Long id;
    private Long schedulesId;
    private Long exerciseId;
    private Long sessionId;
    private String sessionTime;
    private String nutrition;
    private Long statusSessions;
    private Long sessionNo;
    private String location;
    private String description;
    private Long status;

}
