package com.example.UniversityManagementSystem.service;

import com.example.UniversityManagementSystem.model.Course;
import com.example.UniversityManagementSystem.model.EnrolledCourse;
import com.example.UniversityManagementSystem.model.Student;
import com.example.UniversityManagementSystem.repository.EnrolledCourseRepository;
import com.example.UniversityManagementSystem.repository.StudentRepository;
import com.example.UniversityManagementSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrolledCourseService {
    @Autowired
    private EnrolledCourseRepository enrolledCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Page<EnrolledCourse> findMarksheetWithPagination(Long studentId, Integer currentPage) {
        int pageSize=10;
        Pageable pageable= PageRequest.of(currentPage-1, pageSize);
        Page<EnrolledCourse> marksheetPage= enrolledCourseRepository.findByStudentId(studentId, pageable);
        return marksheetPage;
    }

    public boolean saveEnrolledCourseDetail(EnrolledCourse enrolledCourse){
        enrolledCourse=enrolledCourseRepository.save(enrolledCourse);
        return true;

    }

    public void saveEnrolledCourseDetailWithManyCoursesAndOneStudent(List<Course> courses, Student student) {
        student= studentRepository.findById(student.getId()).orElseThrow();
        for(Course course: courses){
            EnrolledCourse enrolledCourse=new EnrolledCourse();
            enrolledCourse.setStudent(student);
            student.getEnrolledCourses().add(enrolledCourse);
            enrolledCourse.setCourse(course);
            enrolledCourse.setMark(null);
            enrolledCourseRepository.save(enrolledCourse);

        }
    }


    public void saveEnrolledCourseDetailWithOneCoursesToManyStudent(List<Student> students, Course course) {
        for(Student student: students){
            EnrolledCourse enrolledCourse=new EnrolledCourse();
            enrolledCourse.setStudent(student);
            enrolledCourse.setCourse(course);
            enrolledCourseRepository.save(enrolledCourse);
        }
    }


    public List<EnrolledCourse> findAllEnrolledCourseByCourseCode(String courseCode) {
        return enrolledCourseRepository.findAllByCourseCode(courseCode);
    }

    public boolean updateEnrolledCourseMark(Double changeMark, Long enrolledCourseId) {
        if(enrolledCourseRepository.existsById(enrolledCourseId) && changeMark!=null){
            EnrolledCourse enrolledCourse=enrolledCourseRepository.findById(enrolledCourseId).orElseThrow();
            enrolledCourse.setMark(changeMark);
            enrolledCourse.setMarkUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

            enrolledCourseRepository.save(enrolledCourse);
            return true;
        }
        else{
            return false;
        }

    }
}
