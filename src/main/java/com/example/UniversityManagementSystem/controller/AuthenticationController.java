package com.example.UniversityManagementSystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String displayHomePage(){

        return "login/login.html";
    }

    @GetMapping("/dashboard")
    public String redirectAfterLogin(Authentication authentication){
        if(authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"))){
            return "redirect:/admin/dashboard";
        }
        else if(authentication.getAuthorities().stream().anyMatch(a->a.getAuthority().equalsIgnoreCase("ROLE_STUDENT"))){
            return "redirect:/student/dashboard";
        } else{
            return "redirect:/teacher/dashboard";
        }


    }
}
