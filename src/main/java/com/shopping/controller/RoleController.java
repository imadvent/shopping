package com.shopping.controller;

import com.shopping.dto.RoleRequest;
import com.shopping.dto.RoleResponse;
import com.shopping.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/role")
    public ResponseEntity<?> addRole(@RequestBody RoleRequest userInfo) {

        RoleResponse roleResponse = roleService.addRole(userInfo);
        return new ResponseEntity<>(roleResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getrole")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //it will get precedence over authorizeHttpRequest()
    public ResponseEntity<List<RoleResponse>> getAllRole() {

        List<RoleResponse> responseList = roleService.getAll();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
