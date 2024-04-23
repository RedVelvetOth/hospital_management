package com.example.hospital_management.security.service;

import com.example.hospital_management.security.entities.AppRoles;
import com.example.hospital_management.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRoles addRole(String rolef);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
