package com.example.user_service.service.implement;

import com.example.user_service.dao.statement.PtDao;
import com.example.user_service.model.DTO.response.PagedResponse;
import com.example.user_service.model.DTO.response.PtResponse;
import com.example.user_service.model.entity.Certifications;
import com.example.user_service.model.entity.Image;
import com.example.user_service.model.entity.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PtDao ptDao;
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Users getUserByUserId(Long userId) {
        Users info = userRepository.getUsersByUserId(userId);
        return info;
    }

    @Override
    public PagedResponse<PtResponse> getAllPt(Integer page, Integer size) {
        List<PtResponse> list = ptDao.getAllPT();
        if (page == null || size == null || page < 0 || size <= 0) {
            return new PagedResponse<>(page, size, 0L, Collections.emptyList());
        }
        int start = page * size;
        int end = Math.min(start + size, list.size());
        if (start >= list.size()) {
            return new PagedResponse<>(page, size, (long) list.size(), Collections.emptyList());
        }
        List<PtResponse> pagedData = list.subList(start, end);
        return new PagedResponse<>(page, size, (long) list.size(), pagedData);
    }

    @Override
    public PagedResponse<PtResponse> getAllRegisterPt(Integer page, Integer size) {
        List<PtResponse> list = ptDao.getListRegisPT();
        if (page == null || size == null || page < 0 || size <= 0) {
            return new PagedResponse<>(page, size, 0L, Collections.emptyList());
        }
        int start = page * size;
        int end = Math.min(start + size, list.size());
        if (start >= list.size()) {
            return new PagedResponse<>(page, size, (long) list.size(), Collections.emptyList());
        }
        List<PtResponse> pagedData = list.subList(start, end);
        return new PagedResponse<>(page, size, (long) list.size(), pagedData);
    }
}
