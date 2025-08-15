package com.example.UniversityManagementSystem.repository;

import com.example.UniversityManagementSystem.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    Page<Department> findAll(Pageable pageable);
}
