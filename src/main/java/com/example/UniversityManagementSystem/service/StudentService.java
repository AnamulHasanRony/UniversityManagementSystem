package com.example.UniversityManagementSystem.service;


import com.example.UniversityManagementSystem.model.Student;
import com.example.UniversityManagementSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean addStudentToDataBase(Student student) {
        if(studentRepository.existsById(student.getId())){
            return true;
        }
        else{
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            student=studentRepository.save(student);
            return false;
        }
    }

    public long numberOfStudentExist() {
        return studentRepository.count();
    }

    public Page<Student> findStudentsListWithPagination(Integer currentPage) {
        int pageSize=10;
        Pageable pageable= PageRequest.of(currentPage-1, pageSize, Sort.by("id").ascending());
        Page<Student> studentsPage= studentRepository.findAll(pageable);
        return studentsPage;
    }

    public boolean findStudentbyId(Long studentId) {
        return studentRepository.existsById(studentId);
    }

    public Student findStudentDetailsbyId(Long studentId) {
        return studentRepository.findById(studentId).orElseGet(null);
    }

    public List<Student> findStudentDetailsbyDepartmentCodeAndLevelAndTerm(String code, int level, int term) {
        return studentRepository.findByDepartmentCodeAndLevelAndTerm(code, level,term);
    }

    public boolean addUpdatedStudentInfoToDataBase(Student studentCarry) {

//        System.out.println( "student serviceee ----     ");
        if(studentRepository.existsById(studentCarry.getId())){
            Student student= studentRepository.findById(studentCarry.getId()).orElseThrow();

//            System.out.println("naame ---- > " + studentCarry.getName());
//            System.out.println("email ---- > " + studentCarry.getEmail());
//            System.out.println("pass ---- > " + studentCarry.getPassword());
//            System.out.println("confirm pass ---- > " + studentCarry.getConfirmPassword());

            if(!studentCarry.getName().isEmpty()){student.setName(studentCarry.getName());
//                System.out.println(studentCarry.getName() + "   get name ");
            }
            if(!studentCarry.getEmail().isEmpty()){student.setEmail(studentCarry.getEmail());}
            if(!studentCarry.getPassword().isEmpty()){student.setPassword(passwordEncoder.encode(studentCarry.getPassword()));}
            studentRepository.save(student);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteStudentByStudentId(Long studentId) {
        if(studentRepository.existsById(studentId)){
            Student student= studentRepository.findById(studentId).orElseThrow();
            student.getDepartment().getStudents().remove(student);
            studentRepository.deleteById(studentId);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Student> findAllByLevelAndTerm(int i, int j) {
        return studentRepository.findAllByLevelAndTerm(i,j);
    }

    public void updatelevelAndTerm(Student student, int i, int j) {
        student.setLevel(i);
        student.setTerm(j);
        studentRepository.save(student);

    }
}
