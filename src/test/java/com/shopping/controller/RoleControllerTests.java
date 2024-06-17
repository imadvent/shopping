package com.shopping.controller;

import com.shopping.dto.RoleRequest;
import com.shopping.dto.RoleResponse;
import com.shopping.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        RoleRequest roleEntity = new RoleRequest();
        roleEntity.setRoleUserName(USER_NAME);
        roleEntity.setRole(ROLE);
        roleEntity.setEmail(EMAIL_ID);
        roleEntity.setPassword(PASSWORD);

        when(roleService.addRole(any(RoleRequest.class))).thenReturn(new RoleResponse());

        ResponseEntity<?> responseEntity = roleController.addRole(roleEntity);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllRole() {

        List<RoleResponse> roleList = Collections.singletonList(new RoleResponse());

        when(roleService.getAll()).thenReturn(roleList);

        assertNotNull(roleList);
    }
}
