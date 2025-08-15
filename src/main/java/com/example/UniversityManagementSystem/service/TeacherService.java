package com.example.UniversityManagementSystem.service;


import com.example.UniversityManagementSystem.model.Course;
import com.example.UniversityManagementSystem.model.Teacher;
import com.example.UniversityManagementSystem.repository.DepartmentRepository;
import com.example.UniversityManagementSystem.repository.TeacherRepository;
//import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;


    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    public boolean addTeacherToDatabase(Teacher teacher){

        if(teacherRepository.existsById(teacher.getId())){
            return true;
        }
        else{
            teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
            teacher=teacherRepository.save(teacher);
            return false;
        }
    }

    public long numberOfTeacherExist() {
        return teacherRepository.count();
    }

    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseGet(null);
    }

    public boolean updateTeachersCourse(Teacher teacher, Course course) {
        if(teacher.getCourses().stream().filter(
                        c-> c.getCode().equalsIgnoreCase(course.getCode()))
                .collect(Collectors.toList()).isEmpty()){
            teacher.getCourses().add(course);

            teacher=teacherRepository.save(teacher);

            return true;

        }

        return false;
    }

    public Page<Teacher> findTeachersListWithPagination(int currentPage) {
        int pageSize=10;
        Pageable pageable= PageRequest.of(currentPage-1, pageSize, Sort.by("id").ascending());
        return teacherRepository.findAll(pageable);

    }


    public boolean addUpdatedTeacherToDatabase(Teacher teacherCarry) {

        if(teacherRepository.existsById(teacherCarry.getId())){
            Teacher teacher=teacherRepository.findById(teacherCarry.getId()).get();

            System.out.println("Carryget Name ---> "+ teacherCarry.getName());
            System.out.println("Orginalget Name ---> "+ teacher.getName());
            System.out.println("Carryget Pass ---> "+ teacherCarry.getPassword());
            System.out.println("Orginalget Pass ---> "+ teacher.getPassword());
            System.out.println("Carryget Email ---> "+ teacherCarry.getEmail());
            System.out.println("Orginalget Email ---> "+ teacher.getEmail());
            System.out.println("Carryget Desi ---> "+ teacherCarry.getDesignation());
            System.out.println("Orginalget Desig ---> "+ teacher.getDesignation());
            if(teacherCarry.getName()!=null && !teacherCarry.getName().isEmpty()) {teacher.setName(teacherCarry.getName());}
            if(teacherCarry.getEmail()!=null && !teacherCarry.getEmail().isEmpty()) teacher.setEmail(teacherCarry.getEmail());
            if(teacherCarry.getPassword()!=null && !teacherCarry.getPassword().isEmpty()) teacher.setPassword(passwordEncoder.encode(teacherCarry.getPassword()));
            if(teacherCarry.getDesignation()!=null && !teacherCarry.getDesignation().isEmpty()) teacher.setDesignation(teacherCarry.getDesignation());
            System.out.println("t_id t_pass" + teacher.getName() + teacher.getPassword());
            System.out.println("teacher course" + teacher.getCourses().toString());

                teacherRepository.save(teacher);
                return true;

        }
        return false;
    }

    @Transactional
    public boolean deleteTeacherByTeacherId(Long teacherId) {
        if(teacherRepository.existsById(teacherId)){

            Teacher teacher=teacherRepository.findById(teacherId).orElseThrow();

            for(Course course: teacher.getCourses()){
                course.getTeachers().remove(teacher);
            }
            teacher.getCourses().clear();

            if (teacher.getDepartment() != null) {
                teacher.getDepartment().getTeachers().remove(teacher);
            }

            teacherRepository.delete(teacher);

            return true;
        }
        return false;
    }
}
