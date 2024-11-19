package com.example.user_service.service;

import com.example.user_service.model.DTO.response.PagedResponse;
import com.example.user_service.model.DTO.response.PtResponse;
import com.example.user_service.model.entity.Users;

import java.util.List;

public interface UserService {
    Users getUserByUserId(Long userId);

    public PagedResponse<PtResponse> getAllPt(Integer page, Integer size);

    public PagedResponse<PtResponse> getAllRegisterPt(Integer page, Integer size);
}
