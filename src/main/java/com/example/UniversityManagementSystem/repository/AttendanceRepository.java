package com.example.UniversityManagementSystem.repository;

import com.example.UniversityManagementSystem.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    List<Attendance> findByLessonId(Long lessonId);
}
