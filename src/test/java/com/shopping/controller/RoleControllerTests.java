package com.shopping.controller;

import com.shopping.entity.RoleEntity;
import com.shopping.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTests {

    private static final int ROLE_ID = 1;
    private static final String USER_NAME = "ADMIN_USER";
    private static final String ROLE = "ADMIN";
    private static final String EMAIL_ID = "admin@test.com";
    private static final String PASSWORD = "adminpwd";

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRole() {

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(ROLE_ID);
        roleEntity.setRoleUserName(USER_NAME);
        roleEntity.setRole(ROLE);
        roleEntity.setEmail(EMAIL_ID);
        roleEntity.setPassword(PASSWORD);

        when(roleService.addRole(any(RoleEntity.class))).thenReturn(roleEntity);

        RoleEntity responseEntity = roleController.addRole(roleEntity);

        assertEquals(ROLE_ID, responseEntity.getRoleId());
    }

    @Test
    public void testGetAllRole() {

        List<RoleEntity> roleList = Collections.singletonList(new RoleEntity());

        when(roleService.getAll()).thenReturn(roleList);

        List<RoleEntity> responseEntity = roleController.getAllRole();

        assertEquals(roleList, responseEntity);
    }
}
