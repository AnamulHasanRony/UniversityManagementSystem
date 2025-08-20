package com.example.UniversityManagementSystem.service;


import com.example.UniversityManagementSystem.model.Course;
import com.example.UniversityManagementSystem.model.Teacher;
import com.example.UniversityManagementSystem.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrolledCourseService enrolledCourseService;

    @Autowired
    private StudentService studentService;



    public boolean addCourseToDataBase(Course course) {
        if(courseRepository.existsById(course.getCode())){
            return true;
        }
        else{
            courseRepository.save(course);
            return false;
        }
    }

    public long numberOfCourseExist() {
        return courseRepository.count();
    }

    public List<Course> findAllCourseBasedOnDepartmentCodeAndLevelAndTerm(String deptCode, int level, int term) {
        return courseRepository.findByDepartment_CodeAndLevelAndTerm(deptCode, level,term);
    }

    public Course findCourseByCourseCode(String courseCode) {
        return courseRepository.findById(courseCode).orElseGet(null);
    }

    public Page<Course> findCoursesListWithPagination(Integer currentPage) {
            int pageSize=10;
            Pageable pageable= PageRequest.of(currentPage-1, pageSize, Sort.by("code").ascending());
            Page<Course> coursesPage= courseRepository.findAll(pageable);
            return coursesPage;

    }
    @Transactional
    public boolean addUpdateCourseToDataBase(String courseCode, Long teacherRemains,String courseName, float courseCredit) {

        if(courseRepository.existsById(courseCode)){
            Course course= courseRepository.findById(courseCode).orElseGet(null);

            if(!courseName.isEmpty()){
                course.setName(courseName);
            }

                course.setCredit(courseCredit);

            if(teacherRemains==0){
                for(Teacher teacher: course.getTeachers())
                {
                    teacher.getCourses().remove(course);
                }
                course.getTeachers().clear();
            }

            course=courseRepository.save(course);

            return true;


        }
        return false;

    }

    @Transactional
    public boolean deleteCourseByCourseCode(String courseCode) {
        if(courseRepository.existsById(courseCode)){
            Course course=courseRepository.findById(courseCode).get();


            for(Teacher teacher: course.getTeachers()){
                teacher.getCourses().remove(course);
            }
            course.getTeachers().clear();
            if (course.getDepartment() != null) {
                course.getDepartment().getCourses().remove(course);
            }

            System.out.println("--course enrollment size = " + course.getEnrolledCourses().size());


            courseRepository.delete(course);


//            if(courseRepository.existsById(courseCode)){
//                System.out.println("exist after deleted ");
//                System.out.println(courseRepository.findById(courseCode));
//            }
            return true;
        }
        return false;

    }
}
