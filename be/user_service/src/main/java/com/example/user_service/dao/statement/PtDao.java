package com.example.user_service.dao.statement;

import com.example.user_service.dao.mapper.UserMapper;
import com.example.user_service.model.DTO.response.PtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PtDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    GeneratedKeyHolder holder =new GeneratedKeyHolder();
    private UserMapper userMapper = new UserMapper();

    public List<PtResponse> getAllPT() {
        String query = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.phone_number, u.address, " +
                "u.age, u.gender, u.date_birth, ce.specialization, ce.certification_name, " +
                "ce.img_certification, i.path_name " +
                "FROM users u " +
                "JOIN certifications ce ON u.user_id = ce.user_id " +
                "JOIN image i ON i.user_id = u.user_id " +
                "JOIN user_role ur ON ur.uid = u.user_id " +
                "WHERE ur.rid = 3 AND i.type_img = 1 AND ce.status = 2";
        return jdbcTemplate.query(query, userMapper.getPtMap());
    }

    public List<PtResponse> getListRegisPT() {
        String query = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.phone_number, u.address, " +
                "u.age, u.gender, u.date_birth, ce.specialization, ce.certification_name, " +
                "ce.img_certification, i.path_name " +
                "FROM users u " +
                "JOIN certifications ce ON u.user_id = ce.user_id " +
                "JOIN image i ON i.user_id = u.user_id " +
                "JOIN user_role ur ON ur.uid = u.user_id " +
                "WHERE ur.rid = 3 AND i.type_img = 1 AND ce.status = 1";
        return jdbcTemplate.query(query, userMapper.getPtMap());
    }

    public Integer deleteRolePt(Long userId){
        String query = "DELETE FROM user_role WHERE uid = ?";
        Integer rowsAffected = jdbcTemplate.update(query, userId);
        return rowsAffected;
    }

    public Integer deleteCertification(Long userId){
        String query = "delete from certifications where user_id = ?";
        Integer rowsAffected = jdbcTemplate.update(query, userId);
        return rowsAffected;
    }

    public Integer acceptsPT(Long userId){
        String query = "update certifications set status = 2 " +
                "where user_id = ?";
        Integer rowAffected = jdbcTemplate.update(query, userId);
        return rowAffected;
    }
}

