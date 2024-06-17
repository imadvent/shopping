package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {

    private int roleId;
    private String roleUserName;
    private String role;
    private String email;
    private String password;
}
