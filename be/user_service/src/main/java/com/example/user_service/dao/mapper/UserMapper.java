package com.example.user_service.dao.mapper;

import com.example.user_service.model.DTO.response.PtResponse;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper {
     private RowMapper<PtResponse> PtMap = ((rs, rowNum) -> {
             PtResponse ptResponse = new PtResponse();
             ptResponse.setUserId(rs.getLong("user_id"));
             ptResponse.setFirstName(rs.getString("first_name"));
             ptResponse.setLastName(rs.getString("last_name"));
             ptResponse.setEmail(rs.getString("email"));
             ptResponse.setPhone_number(rs.getString("phone_number"));
             ptResponse.setAddress(rs.getString("address"));
             ptResponse.setAge(rs.getInt("age"));
             ptResponse.setGender(rs.getInt("gender"));
             ptResponse.setDateOfBirth(rs.getString("date_birth"));
             ptResponse.setSpecialization(rs.getString("specialization"));
             ptResponse.setImgCertification(rs.getString("img_certification"));
             ptResponse.setCertificationName(rs.getString("certification_name"));
             ptResponse.setImgAvatar(rs.getString("path_name"));
      return ptResponse;
     });

        public RowMapper<PtResponse> getPtMap() {
                return PtMap;
        }
}
