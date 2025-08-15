package com.example.UniversityManagementSystem.repository;

import com.example.UniversityManagementSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findByDepartmentCodeAndLevelAndTerm(String code, int level, int term);

    List<Student> findAllByLevelAndTerm(int level, int term);
}
