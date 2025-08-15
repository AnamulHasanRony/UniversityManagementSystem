package com.example.UniversityManagementSystem.controller;

import com.example.UniversityManagementSystem.model.Course;
import com.example.UniversityManagementSystem.model.Teacher;
import com.example.UniversityManagementSystem.service.CourseService;
import com.example.UniversityManagementSystem.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

//    fetch(`/api/courses?deptCode=${deptCode}&level=${level}&term=${term}`)

    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> sentTeachersToFormBasedOnDepartmentId(@RequestParam String deptCode){
        List<Teacher> teachers=departmentService.findAllTeacherByDepartmentCode(deptCode);
        System.out.println("teachers size --> " + teachers.size());
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> sentCoursesToFormBasedOnDepartmentCodeAndLevelAndTerm(
            @RequestParam String deptCode,
            @RequestParam int level,
            @RequestParam int term){
        List<Course> courses= courseService.findAllCourseBasedOnDepartmentCodeAndLevelAndTerm(deptCode, level,term);
        return ResponseEntity.ok(courses);
    }

}
