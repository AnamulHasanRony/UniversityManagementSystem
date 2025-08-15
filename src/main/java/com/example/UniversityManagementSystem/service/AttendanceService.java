package com.example.UniversityManagementSystem.service;

import com.example.UniversityManagementSystem.model.Attendance;
import com.example.UniversityManagementSystem.model.Users;
import com.example.UniversityManagementSystem.repository.AttendanceRepository;
import com.example.UniversityManagementSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private UsersRepository usersRepository;

    public List<Attendance> getAttendanceOfALesson(Long lessonId) {
        return attendanceRepository.findByLessonId(lessonId);

    }

    public boolean changeAttendance(Long attendanceId, Long presentStatus) {
        if(attendanceRepository.existsById(attendanceId)){
            Attendance attendance=attendanceRepository.findById(attendanceId).orElseThrow();
            attendance.setPresentStatus(presentStatus);
            Users user = usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

            attendance.setAttendanceTaker(user.getTeacher().getName()+" ( "+ user.getTeacher().getId()+" ) ");
            attendanceRepository.save(attendance);
            return  true;
        }
        else{
            return false;
        }
    }

    public String findAttendanceLessonId(Long attendanceId) {
        return attendanceRepository.findById(attendanceId).get().getLesson().getId().toString();
    }
}
