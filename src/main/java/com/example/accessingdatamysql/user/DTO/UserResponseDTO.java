package com.example.accessingdatamysql.user.DTO;

import com.example.accessingdatamysql.authentication.Role;

import java.util.List;

public class UserResponseDTO {
    private Integer id;
    private String username;
    private List<Role> roles;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
