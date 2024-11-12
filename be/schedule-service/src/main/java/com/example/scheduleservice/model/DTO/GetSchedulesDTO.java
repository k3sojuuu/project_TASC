package com.example.scheduleservice.model.DTO;

import com.example.scheduleservice.model.Schedules;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSchedulesDTO {
    private Long userId;
    private List<Schedules> schedulesList;
}
