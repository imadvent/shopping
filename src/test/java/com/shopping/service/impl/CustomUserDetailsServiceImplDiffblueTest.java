package com.shopping.service.impl;

import com.shopping.dao.RoleDao;
import com.shopping.entity.RoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomUserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CustomUserDetailsServiceImplDiffblueTest {
    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @MockBean
    private RoleDao roleDao;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        // Arrange
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setEmail("jane.doe@example.org");
        roleEntity.setPassword("iloveyou");
        roleEntity.setRole("Role");
        roleEntity.setRoleId(1);
        roleEntity.setRoleUserName("janedoe");
        Optional<RoleEntity> ofResult = Optional.of(roleEntity);
        when(roleDao.findByRoleUserName(Mockito.any())).thenReturn(ofResult);

        // Act
        UserDetails actualLoadUserByUsernameResult = customUserDetailsServiceImpl.loadUserByUsername("janedoe");

        // Assert
        verify(roleDao).findByRoleUserName(eq("janedoe"));
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
        assertEquals(1, authorities.size());
        assertEquals("Role", ((List<? extends GrantedAuthority>) authorities).get(0).getAuthority());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        // Arrange
        Optional<RoleEntity> emptyResult = Optional.empty();
        when(roleDao.findByRoleUserName(Mockito.any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsServiceImpl.loadUserByUsername("janedoe"));
        verify(roleDao).findByRoleUserName(eq("janedoe"));
    }
}
