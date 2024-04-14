package com.shopping.service;

import com.shopping.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    RoleEntity addRole(RoleEntity userInfo);

    List<RoleEntity> getAll();
}
