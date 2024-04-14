package com.shopping.dao;

import com.shopping.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleUserName(String username);

}
