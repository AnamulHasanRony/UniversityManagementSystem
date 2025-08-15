package com.example.UniversityManagementSystem.controller;


import com.example.UniversityManagementSystem.model.*;
import com.example.UniversityManagementSystem.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EnrolledCourseService enrolledCourseService;

    @Autowired
    private StudentService studentService;



    @GetMapping("/dashboard")
    public ModelAndView displayTeacherDashboard(Model model){
        ModelAndView modelAndView=new ModelAndView("teacher/dashboard.html");
        String username= (String)model.getAttribute("loggedInUsername");
        Users user=usersService.findUserByUsername(username);
        return modelAndView;
    }

    //-----------------------------------------------------------------------------
//                        Teacher's Info Display
//-----------------------------------------------------------------------------
    @GetMapping("/profileInfo")
    public ModelAndView displayTeacherInformation(Model model){
        String username=(String)model.getAttribute("loggedInUsername");
        ModelAndView modelAndView=new ModelAndView("teacher/profileInfo.html");
        Users user= usersService.findUserByUsername(username);
        modelAndView.addObject("teacher", user.getTeacher());
        return modelAndView;
    }
    //---------------------------------------------------------------------------
//                        Get CourseList section
//-----------------------------------------------------------------------------

    @GetMapping("/getCourseList")
    public ModelAndView displayCourseList(Model model){
        ModelAndView modelAndView=new ModelAndView("teacher/getCourseList.html");
        String username= (String)model.getAttribute("loggedInUsername");
        Users user=usersService.findUserByUsername(username);
        model.addAttribute("courses",user.getTeacher().getCourses());
        return modelAndView;
    }
    //---------------------------------------------------------------------------
//                        Lesson create,update,delete and attendence section
//-----------------------------------------------------------------------------


    @GetMapping({"/getLesson","/lesson/create", "/lesson/delete", "/lesson/update"})
    public ModelAndView displayLessonOfACourse(Model model, @RequestParam(required = true) String courseCode,
                                               @RequestParam(required = false) Integer currentPage){
        if (currentPage == null) {
            currentPage=1;
        }

        String username=(String)model.getAttribute("loggedInUsername");
        Users user= usersService.findUserByUsername(username);
        Teacher teacher=user.getTeacher();
        Course course=courseService.findCourseByCourseCode(courseCode);
       model.addAttribute("course", course);
       model.addAttribute("newLesson", new Lesson());
        model.addAttribute("updateLesson", new Lesson());
       model.addAttribute("lessons", course.getLessons());

        ModelAndView modelAndView=new ModelAndView("teacher/getLesson.html");
        return modelAndView;

    }

    @PostMapping("/lesson/create")
    public String createLessonOfACourse(Model model, @RequestParam(required = true) String courseCode,
                                        @ModelAttribute("newLesson") Lesson lesson){

        if(lesson.getName().isEmpty() || lesson.getDescription().isEmpty()){
            String username=(String)model.getAttribute("loggedInUsername");
            Users user= usersService.findUserByUsername(username);
            Teacher teacher=user.getTeacher();
            Course course=courseService.findCourseByCourseCode(courseCode);
            model.addAttribute("course", course);
            model.addAttribute("newLesson", new Lesson());
            model.addAttribute("updateLesson", new Lesson());
            model.addAttribute("lessons", course.getLessons());

            model.addAttribute("errorMessage", "Lesson name or lesson Details caan not be null");
            return "teacher/getLesson.html";


        }
      lessonService.createLesson(lesson, courseCode);
      return  "redirect:/teacher/getLesson?courseCode=" + courseCode;

    }


    @PostMapping("/lesson/update")
    public String updateLessonOfACourse(Model model, @RequestParam(required = true) String courseCode,
                                        @ModelAttribute("updateLesson") Lesson lesson){

        System.out.println(lesson.getName() +" " + lesson.getDescription());
        boolean alreadyExist;
        if(lesson.getId()!=null){
             alreadyExist=lessonService.updateLesson(lesson, courseCode);

        }
        else{
            alreadyExist=false;
        }

        if(!alreadyExist){
            String username=(String)model.getAttribute("loggedInUsername");
            Users user= usersService.findUserByUsername(username);
            Teacher teacher=user.getTeacher();
            Course course=courseService.findCourseByCourseCode(courseCode);
            model.addAttribute("course", course);
            model.addAttribute("newLesson", new Lesson());
            model.addAttribute("updateLesson", new Lesson());
            model.addAttribute("lessons", course.getLessons());

            model.addAttribute("updateErrorMessage", "Lesson Id do not exist");
            return "teacher/getLesson.html";

        }

        return  "redirect:/teacher/getLesson?courseCode=" + courseCode;

    }



    @PostMapping("/lesson/delete")
    public String deleteLessonOfACourse(Model model, @RequestParam(required = true) String courseCode,
                                        @RequestParam(required = true) Long lessonId){

        boolean alreadyExist;
        if(lessonId!=null){
            alreadyExist=lessonService.deleteLesson(lessonId, courseCode);

        }
        else{
            alreadyExist=false;
        }

        if(!alreadyExist){
            String username=(String)model.getAttribute("loggedInUsername");
            Users user= usersService.findUserByUsername(username);
            Teacher teacher=user.getTeacher();
            Course course=courseService.findCourseByCourseCode(courseCode);
            model.addAttribute("course", course);
            model.addAttribute("newLesson", new Lesson());
            model.addAttribute("updateLesson", new Lesson());
            model.addAttribute("lessons", course.getLessons());

            model.addAttribute("updateErrorMessage", "Lesson Id do not exist");
            return "teacher/getLesson.html";

        }

        return  "redirect:/teacher/getLesson?courseCode=" + courseCode;

    }



    @GetMapping("/lesson/getAttendance")
    public ModelAndView displayAttendanceOfALesson(Model model, @RequestParam(required = true) Long lessonId){


        List<Attendance> attendances= attendanceService.getAttendanceOfALesson(lessonId);
        model.addAttribute("attendances", attendances);

        ModelAndView modelAndView=new ModelAndView("teacher/getAttendance.html");
        return modelAndView;

    }

    @PostMapping("/lesson/getAttendance/change")
    public String changeAttendanceOfAStudent(Model model, @RequestParam(required = true) Long attendanceId,
                                                   @RequestParam(required = true) Long presentStatus){

       boolean success=attendanceService.changeAttendance(attendanceId, presentStatus);
        if(success){
            System.out.println("attendance updated");
        }
        else{
            System.out.println("attendance not updated");

        }

        return  "redirect:/teacher/lesson/getAttendance?lessonId="+ attendanceService.findAttendanceLessonId(attendanceId) ;


    }
    //---------------------------------------------------------------------------
//                        Course Marking Section
//-----------------------------------------------------------------------------

    @GetMapping("/getCourseMarking")
    public ModelAndView getCourseMarkingDisplay(Model model, @RequestParam(required = true) String courseCode){
        ModelAndView modelAndView= new ModelAndView("teacher/getCourseMarking.html");
        List<EnrolledCourse>enrolledCourses= enrolledCourseService.findAllEnrolledCourseByCourseCode(courseCode);
        modelAndView.addObject("enrolledCourses",enrolledCourses);
        return modelAndView;

    }

    @PostMapping("/getCourseMarking")
    public String updateCourseMarking(Model model,@RequestParam(required = true) String courseCode,
                                      @RequestParam(required = true) Long enrolledCourseId,
                                      @RequestParam(required = false) Double changeMark, RedirectAttributes redirectAttributes){

        if(changeMark==null){
            return "redirect:/teacher/getCourseMarking?courseCode="+courseCode;

        }
        boolean success=enrolledCourseService.updateEnrolledCourseMark(changeMark, enrolledCourseId);
        if(!success){
            redirectAttributes.addFlashAttribute("errorMessage","Failure!! Course Mark update failed");
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage","Success!!Course Mark updated successfully");
        }
        return "redirect:/teacher/getCourseMarking?courseCode="+courseCode;
    }



    //---------------------------------------------------------------------------
//                        Get Student Marksheet Dashboard
//-----------------------------------------------------------------------------

    @GetMapping("/getMarksheetForm")
    public ModelAndView displayFormToSearchMarksheet(Model model){
        ModelAndView modelAndView=new ModelAndView("teacher/getMarksheetForm.html");
        return modelAndView;
    }

    @PostMapping("/getMarksheetForm")
    public String redirectToMarksheetByStudentId(@RequestParam(required = false) Long studentId, Model model){
        if(studentId==null){

            model.addAttribute("errorMessage", "Student Id can not be null");
            return "teacher/getMarksheetForm.html";

        }
        boolean studentExist= studentService.findStudentbyId(studentId);
        if(!studentExist){
            model.addAttribute("errorMessage", "Student Id do not exist");
            return "teacher/getMarksheetForm.html";
        }
        else{
            return "redirect:/teacher/getMarksheet?studentId="+studentId;
        }

    }


    @GetMapping("/getMarksheet")
    public ModelAndView displayMarksheetOfStudentWithPagination(Model model,
                                                                @RequestParam(required = false) Integer currentPage,
                                                                @RequestParam(required = false) Long studentId){
        if(currentPage==null){
            currentPage=1;
        }

        System.out.println("current page " + currentPage);
        System.out.println("studentId " + studentId);
        ModelAndView modelAndView=new ModelAndView("teacher/getMarksheet.html");
        Page<EnrolledCourse> marksheetsPage= enrolledCourseService.findMarksheetWithPagination(studentId,currentPage);
        List<EnrolledCourse> marksheets= marksheetsPage.getContent();
        modelAndView.addObject("totalPage",marksheetsPage.getTotalPages());
        modelAndView.addObject("marksheets", marksheets);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("student", studentService.findStudentDetailsbyId(studentId));
        return modelAndView;

    }


}
