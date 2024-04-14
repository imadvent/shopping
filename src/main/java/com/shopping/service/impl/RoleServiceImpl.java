package com.shopping.service.impl;

import com.shopping.dao.RoleDao;
import com.shopping.entity.RoleEntity;
import com.shopping.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RoleEntity addRole(RoleEntity userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return roleDao.save(userInfo);
    }

    @Override
    public List<RoleEntity> getAll() {
        return roleDao.findAll();
    }
}
