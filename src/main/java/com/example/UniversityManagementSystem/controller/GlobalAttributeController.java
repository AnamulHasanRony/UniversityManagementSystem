package com.example.UniversityManagementSystem.controller;
import com.example.UniversityManagementSystem.service.StudentService;
import com.example.UniversityManagementSystem.service.TeacherService;
import com.example.UniversityManagementSystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAttributeController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("loggedInUsername", authentication.getName());
            model.addAttribute("roles", authentication.getAuthorities());
        }

        model.addAttribute("appName", "University Management System");
    }
}
