package com.shopping.service.impl;

import com.shopping.dao.RoleDao;
import com.shopping.dto.RoleRequest;
import com.shopping.dto.RoleResponse;
import com.shopping.entity.RoleEntity;
import com.shopping.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.shopping.util.ShoppingUtil.roleRequestMapper;
import static com.shopping.util.ShoppingUtil.roleResponseMapper;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RoleResponse addRole(RoleRequest roleRequest) {

        RoleResponse roleResponse = new RoleResponse();
        RoleEntity userInfo = new RoleEntity();

        roleRequestMapper(roleRequest, userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        roleDao.save(userInfo);
        roleResponseMapper(userInfo, roleResponse);
        return roleResponse;
    }

    @Override
    public List<RoleResponse> getAll() {

        List<RoleResponse> responseList = new ArrayList<>();
        List<RoleEntity> roleEntities = roleDao.findAll();

        roleEntities.forEach(roleList -> {
            RoleResponse roleResponse = new RoleResponse();
            roleResponseMapper(roleList, roleResponse);

            responseList.add(roleResponse);
        });
        return responseList;
    }
}
