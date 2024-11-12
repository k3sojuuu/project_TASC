package com.example.scheduleservice.model.DTO;

import com.example.scheduleservice.model.Sessions;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetSessionDTO {
    private Long schedulesId;
    private List<Sessions> sessionsList;
}
