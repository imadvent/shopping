package com.shopping.dao;

import com.shopping.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleUserName(String username);

}
