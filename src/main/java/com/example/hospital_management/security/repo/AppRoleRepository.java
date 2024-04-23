package com.example.hospital_management.security.repo;

import com.example.hospital_management.security.entities.AppRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRoles, String> {
}
