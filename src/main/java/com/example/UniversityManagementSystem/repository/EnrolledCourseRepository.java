package com.example.UniversityManagementSystem.repository;

import com.example.UniversityManagementSystem.model.EnrolledCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {
    Page<EnrolledCourse> findByStudentId(Long studentId, Pageable pageable);
    List<EnrolledCourse> findAllByCourseCode(String courseCode);

}
