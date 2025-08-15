package com.example.UniversityManagementSystem.service;

import com.example.UniversityManagementSystem.model.*;
import com.example.UniversityManagementSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrolledCourseRepository enrolledCourseRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void createLesson(Lesson lesson, String courseCode) {
        Course course=courseRepository.findById(courseCode).orElseThrow();
        lesson.setCourse(course);
        lesson = lessonRepository.save(lesson);

        List<EnrolledCourse> enrolledCourses=enrolledCourseRepository.findAllByCourseCode(courseCode);
       for(EnrolledCourse enrolledCourse: enrolledCourses){
           if(enrolledCourse.getStudent().getLevel()==course.getLevel() && enrolledCourse.getStudent().getTerm()==course.getTerm()){
               System.out.println(enrolledCourse.getStudent().getName());
               Attendance attendance=new Attendance();
               attendance.setLesson(lesson);
               attendance.setEnrolledCourse(enrolledCourse);
               attendanceRepository.save(attendance);
           }

       }
        //create attendence of the same level enrolled course





    }

    public void updateLessonToNewStudentAlsoWithAttendance( Student student) {
        student=studentRepository.findById(student.getId()).orElseThrow();

        List<Course> courses=courseRepository.findByDepartment_CodeAndLevelAndTerm(student.getDepartment().getCode(), student.getLevel(), student.getTerm());
        for(Course course: courses){
            System.out.println("course ---> "+ course.getName());
            for(Lesson lesson:course.getLessons()){
                System.out.println("lesson name ---> "+ lesson.getName());
                Attendance attendance=new Attendance();
                attendance.setLesson(lesson);
                EnrolledCourse enrolledCourse= student.getEnrolledCourses().stream().filter(c->c.getCourse().getCode()==course.getCode()).findFirst().orElseThrow();
                System.out.println("enrolled course---> " + enrolledCourse.getCourse().getName());
                attendance.setEnrolledCourse(enrolledCourse);
                attendanceRepository.save(attendance);



            }

        }
    }

    public boolean updateLesson(Lesson lessonCarry, String courseCode) {
        if(lessonRepository.existsById(lessonCarry.getId())){
            Lesson lesson=lessonRepository.findById(lessonCarry.getId()).orElseThrow();
            if(!lessonCarry.getName().isEmpty())lesson.setName(lessonCarry.getName());
            if(!lessonCarry.getDescription().isEmpty())lesson.setDescription(lessonCarry.getDescription());

            lessonRepository.save(lesson);
            return true;

        }
        else{
            return false;
        }
    }

    public boolean deleteLesson(Long lessonId, String courseCode) {
        Lesson lesson=lessonRepository.findById(lessonId).orElseThrow();
        Course course=courseRepository.findById(courseCode).orElseThrow();
        course.getLessons().remove(lesson);

        lessonRepository.delete(lesson);
        return true;

    }
}
