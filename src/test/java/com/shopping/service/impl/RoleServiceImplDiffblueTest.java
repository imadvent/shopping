package com.shopping.service.impl;

import com.shopping.dao.RoleDao;
import com.shopping.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RoleServiceImplDiffblueTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleDao roleDao;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Test
    void testAddRole() {
        // Arrange
        when(passwordEncoder.encode(Mockito.any())).thenReturn("secret");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setEmail("jane.doe@example.org");
        roleEntity.setPassword("iloveyou");
        roleEntity.setRole("Role");
        roleEntity.setRoleId(1);
        roleEntity.setRoleUserName("janedoe");
        when(roleDao.save(Mockito.any())).thenReturn(roleEntity);

        RoleEntity userInfo = new RoleEntity();
        userInfo.setEmail("jane.doe@example.org");
        userInfo.setPassword("iloveyou");
        userInfo.setRole("Role");
        userInfo.setRoleId(1);
        userInfo.setRoleUserName("janedoe");

        // Act
        RoleEntity actualAddRoleResult = roleServiceImpl.addRole(userInfo);

        // Assert
        verify(roleDao).save(isA(RoleEntity.class));
        verify(passwordEncoder).encode(isA(CharSequence.class));
        assertEquals("secret", userInfo.getPassword());
        assertSame(roleEntity, actualAddRoleResult);
    }

    @Test
    void testGetAll() {
        // Arrange
        ArrayList<RoleEntity> roleEntityList = new ArrayList<>();
        when(roleDao.findAll()).thenReturn(roleEntityList);

        // Act
        List<RoleEntity> actualAll = roleServiceImpl.getAll();

        // Assert
        verify(roleDao).findAll();
        assertTrue(actualAll.isEmpty());
        assertSame(roleEntityList, actualAll);
    }
}
