package com.example.UniversityManagementSystem.controller;


import com.example.UniversityManagementSystem.model.EnrolledCourse;
import com.example.UniversityManagementSystem.model.Student;
import com.example.UniversityManagementSystem.model.Users;
import com.example.UniversityManagementSystem.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private EnrolledCourseService enrolledCourseService;

    //--------------------------------------------------------------------------
//---------------------------------------------------------------------------
    //-----------------------------------------------------------------------------
//                        Student's Dashboard
//-----------------------------------------------------------------------------
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------
    @GetMapping("/dashboard")
    public ModelAndView displayStudentDashboard(Model model){

        String username=(String)model.getAttribute("loggedInUsername");
        Users user= usersService.findUserByUsername(username);
        ModelAndView modelAndView=new ModelAndView("student/dashboard.html");

        modelAndView.addObject("courseCount", user.getStudent().getEnrolledCourses().size());
        return modelAndView;
    }

    //-----------------------------------------------------------------------------
//                        Student's Info Display
//-----------------------------------------------------------------------------
    @GetMapping("/profileInfo")
    public ModelAndView displayStudentInformation(Model model){
      String username=(String)model.getAttribute("loggedInUsername");
      ModelAndView modelAndView=new ModelAndView("student/profileInfo.html");
        Users user= usersService.findUserByUsername(username);
      modelAndView.addObject("student", user.getStudent());
      return modelAndView;
    }

    //-----------------------------------------------------------------------------
//                        Student's Marksheet Display
//-----------------------------------------------------------------------------

    @GetMapping("/getMarksheet")
    public ModelAndView displayMarksheetOfStudentWithPagination(Model model,
                                                                @RequestParam(required = false) Integer currentPage){
        if(currentPage==null){
            currentPage=1;
        }

        String username=(String)model.getAttribute("loggedInUsername");
        Users user= usersService.findUserByUsername(username);
        Student student=user.getStudent();


        System.out.println("current page " + currentPage);
        System.out.println("studentId " + student.getId());
        ModelAndView modelAndView=new ModelAndView("student/getMarksheet.html");
        Page<EnrolledCourse> marksheetsPage= enrolledCourseService.findMarksheetWithPagination(student.getId(),currentPage);
        List<EnrolledCourse> marksheets= marksheetsPage.getContent();
        modelAndView.addObject("totalPage",marksheetsPage.getTotalPages());
        modelAndView.addObject("marksheets", marksheets);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("student", studentService.findStudentDetailsbyId(student.getId()));

          return modelAndView;

    }

    //-----------------------------------------------------------------------------
//                        Student's Marksheet Display
//-----------------------------------------------------------------------------

    @GetMapping("/getCourseList")
    public ModelAndView displayCourseList(Model model){

        String username=(String)model.getAttribute("loggedInUsername");
        Users user= usersService.findUserByUsername(username);
        Student student=user.getStudent();
        List<EnrolledCourse>enrolledCourses= student.getEnrolledCourses();


        System.out.println("studentId " + student.getId());
        ModelAndView modelAndView=new ModelAndView("student/getCourseList.html");
        modelAndView.addObject("enrolledCourses", enrolledCourses);
        return modelAndView;

    }


    @GetMapping("/getAttendanceAndLesson")
    public ModelAndView displayAttendanceAndLessonList(Model model, @RequestParam(required = true) String courseCode){

        String username=(String)model.getAttribute("loggedInUsername");
        Users user= usersService.findUserByUsername(username);
        Student student=user.getStudent();
        EnrolledCourse enrolledCourse= student.getEnrolledCourses().stream()
                .filter(e -> e.getCourse()
                        .getCode().equalsIgnoreCase(courseCode)).findFirst().get();


        System.out.println("studentId " + student.getId());
        ModelAndView modelAndView=new ModelAndView("student/getAttendanceAndLesson.html");
        modelAndView.addObject("enrolledCourse", enrolledCourse);
        return modelAndView;

    }





}
