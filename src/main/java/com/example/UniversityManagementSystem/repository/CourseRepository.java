package com.example.UniversityManagementSystem.repository;

import com.example.UniversityManagementSystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByDepartment_CodeAndLevelAndTerm(String code, int level, int term);

}
