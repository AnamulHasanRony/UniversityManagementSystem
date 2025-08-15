package com.example.UniversityManagementSystem.createOneAdminByDefault;

import com.example.UniversityManagementSystem.model.Role;
import com.example.UniversityManagementSystem.model.Users;
import com.example.UniversityManagementSystem.repository.RoleRepository;
import com.example.UniversityManagementSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class createOneAdminByDefault implements CommandLineRunner {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null,"ROLE_ADMIN")));
        Role teacherRole = roleRepository.findByName("ROLE_TEACHER")
                .orElseGet(() -> roleRepository.save(new Role(null,"ROLE_TEACHER")));
        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseGet(() -> roleRepository.save(new Role(null,"ROLE_STUDENT")));

        Users findAdmin=usersRepository.findByUsername("admin");
        if (findAdmin==null) {
            Users admin=new Users();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(adminRole);
            usersRepository.save(admin);
            System.out.println("Admin created successfully username: admin / password: admin");
        }

    }
}
