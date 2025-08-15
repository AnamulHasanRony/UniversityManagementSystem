package com.example.UniversityManagementSystem.service;

import com.example.UniversityManagementSystem.model.CustomUserDetails;
import com.example.UniversityManagementSystem.model.Users;
import com.example.UniversityManagementSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=usersRepository.findByUsername(username);
        if(user==null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new CustomUserDetails(user);
    }
}
