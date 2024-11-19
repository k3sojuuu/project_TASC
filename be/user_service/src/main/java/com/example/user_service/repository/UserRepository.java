package com.example.user_service.repository;

import com.example.user_service.model.DTO.response.PtResponse;
import com.example.user_service.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Users findByUsername(String username);
    @Query(value = "SELECT user_id FROM users WHERE username = :username", nativeQuery = true)
    Long getUserIdByUsername(@Param("username") String username);
    @Query(value = "select r.role_name from user_role us," +
            "users u, " +
            "roles r " +
            "where us.uid=u.user_id and r.id=us.rid and u.username=:username",nativeQuery = true)
    List<String> findRoles(@Param("username") String username);

    Users getUsersByUserId(Long userId);

    @Query(value = "SELECT u.user_id, u.first_name, u.last_name, u.email, u.phone_number, u.address, " +
            "u.age, u.gender, u.date_birth, ce.specialization, ce.certification_name, " +
            "ce.img_certification, i.path_name " +
            "FROM users u " +
            "JOIN certifications ce ON u.user_id = ce.user_id " +
            "JOIN image i ON i.user_id = u.user_id " +
            "JOIN user_role ur ON ur.uid = u.user_id " +
            "WHERE ur.rid = 3 AND i.type_img = 1 " +
            "LIMIT :size OFFSET :offset", nativeQuery = true)
    List<PtResponse> getAllPT(@Param("offset") Long offset,
                              @Param("size") Long size);



}