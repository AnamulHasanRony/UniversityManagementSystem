package com.example.UniversityManagementSystem.service;

import com.example.UniversityManagementSystem.model.Role;
import com.example.UniversityManagementSystem.model.Student;
import com.example.UniversityManagementSystem.model.Teacher;
import com.example.UniversityManagementSystem.model.Users;
import com.example.UniversityManagementSystem.repository.RoleRepository;
import com.example.UniversityManagementSystem.repository.StudentRepository;
import com.example.UniversityManagementSystem.repository.TeacherRepository;
import com.example.UniversityManagementSystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;


    public void createUsersForStudent( Student student1) {
        Users user= new Users();
        Student student= studentRepository.findById(student1.getId()).orElseThrow();
        Role role=roleRepository.findByName("ROLE_STUDENT").orElseGet(null);
        user.setStudent(student);
        user.setRole(role);
        user.setUsername("student_"+student.getId().toString());
        user.setPassword(student.getPassword());
        student.setUsers(user);
        usersRepository.save(user);
    }

    public void updateStudentPassword(Student student1) {
        Student student= studentRepository.findById(student1.getId()).orElseThrow();
        Users user= student.getUsers();
        user.setPassword(student.getPassword());

    }
    public void createUserForTeacher(Teacher teacher) {
        teacher= teacherRepository.findById(teacher.getId()).orElseThrow();

      System.out.println("-------------------------------");
//      System.out.println(teacher.getCreatedAt());
      System.out.println("-------------------------------");

        Users user= new Users();
        Role role=roleRepository.findByName("ROLE_TEACHER").orElseThrow();
        user.setPassword(teacher.getPassword());
        user.setUsername("teacher_"+teacher.getId().toString());
        user.setTeacher(teacher);

        user.setRole(role);
      System.out.println("-------------------------------");
      System.out.println(user.getCreatedAt()+ "user created ");
      System.out.println("-------------------------------");
        usersRepository.save(user);

    }

    public void updateTeacherPassword(Teacher teacher1) {
        Teacher teacher=teacherRepository.findById(teacher1.getId()).orElseThrow();
        Users user=usersRepository.findByUsername("teacher_"+teacher.getId().toString());
        user.setPassword(teacher.getPassword());
        usersRepository.save(user);
    }

    public Users findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
