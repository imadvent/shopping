package com.shopping.controller;

import com.shopping.entity.RoleEntity;
import com.shopping.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @PostMapping(value = "/role")
    public RoleEntity addRole(@RequestBody RoleEntity userInfo) {
        return roleService.addRole(userInfo);
    }

    @GetMapping(value = "/getrole")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //it will get precedence over authorizeHttpRequest()
    public List<RoleEntity> getAllRole() {
        return roleService.getAll();
    }
}
