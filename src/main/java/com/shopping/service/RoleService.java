package com.shopping.service;

import com.shopping.dto.RoleRequest;
import com.shopping.dto.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse addRole(RoleRequest userInfo);

    List<RoleResponse> getAll();
}
