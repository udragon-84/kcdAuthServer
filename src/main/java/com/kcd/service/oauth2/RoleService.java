package com.kcd.service.oauth2;

import jakarta.servlet.http.HttpServletRequest;

public interface RoleService {
    void addRoleUser(HttpServletRequest request);
    void addRoleAdmin(HttpServletRequest request);
    void addCustomRole(String roleName, HttpServletRequest request);
    void deleteRole(String roleName, HttpServletRequest request);
}
