package com.example.user_service.repository;

import com.example.user_service.model.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Integer> {
    boolean existsUserRolesByRidAndUid(int rid,int uid);

}
