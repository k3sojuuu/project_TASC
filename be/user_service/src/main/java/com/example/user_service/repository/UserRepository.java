package com.example.user_service.repository;

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
}
