package com.example.user_service.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private Integer page;
    private Integer size;
    private Long totalItems;
    private List<T> data;
}
