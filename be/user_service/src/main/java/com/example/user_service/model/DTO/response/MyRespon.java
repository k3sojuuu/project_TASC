package com.example.user_service.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class MyRespon {
    private Object data;
    private String message;
    private int status;
}
