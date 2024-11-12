package com.example.scheduleservice.model.DTO;

import com.example.scheduleservice.model.Exercise;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionOnlineDTO {
    private Long schedulesId;
    private String sessionName;
    private String nutrition;
    private Long statusSessions;
    private Long sessionNo;
    private String description;
}

