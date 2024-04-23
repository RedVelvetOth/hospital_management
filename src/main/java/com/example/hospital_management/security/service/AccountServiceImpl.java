package com.example.hospital_management.security.service;

import com.example.hospital_management.security.entities.AppRoles;
import com.example.hospital_management.security.entities.AppUser;
import com.example.hospital_management.security.repo.AppRoleRepository;
import com.example.hospital_management.security.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) throw new RuntimeException("This user already exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");
        AppUser user = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return appUserRepository.save(user);
    }

    @Override
    public AppRoles addRole(String role) {
        AppRoles appRoles = appRoleRepository.findById(role).orElse(null);
        if (appRoles != null) throw new RuntimeException("This role already exists");
        appRoles = AppRoles.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRoles);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRoles appRoles = appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRoles);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRoles appRoles = appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRoles);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
