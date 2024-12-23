package com.example.scheduleservice.model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class MyResponse {
    private Object data;
    private String message;
    private int status;
}
