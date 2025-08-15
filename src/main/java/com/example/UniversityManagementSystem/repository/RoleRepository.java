package com.example.UniversityManagementSystem.repository;


import com.example.UniversityManagementSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleStudent);

//    findByName(String name)
//    Role findByName(String roleAdmin);
    
}
