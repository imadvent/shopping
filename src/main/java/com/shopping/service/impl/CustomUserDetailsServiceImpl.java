package com.shopping.service.impl;

import com.shopping.dao.RoleDao;
import com.shopping.entity.RoleEntity;
import com.shopping.security.RoleUserDetails;
import com.shopping.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<RoleEntity> roleUser = roleDao.findByRoleUserName(username);
        return roleUser.map(RoleUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));
    }
}
