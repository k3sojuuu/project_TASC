package com.example.scheduleservice.model.DTO;

import com.example.scheduleservice.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetExerciserDTO {
    private Long sessionId;
    private List<Exercise> exerciseList = new ArrayList<>();
}
