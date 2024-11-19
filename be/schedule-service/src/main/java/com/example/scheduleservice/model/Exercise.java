package com.example.scheduleservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "session_id")
    private Long sessionId;
    @Column(name ="exercise_name")
    private String exerciseName;
    @Column(name = "exe_set")
    private Long exeSet;
    @Column(name ="exe_rep")
    private Long exeRep;
    private String descriptions;
    private Long status;
    @Column(name ="video_path")
    private String videoPath;
}

