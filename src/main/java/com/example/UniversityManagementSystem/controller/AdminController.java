package com.example.UniversityManagementSystem.controller;


import com.example.UniversityManagementSystem.model.*;
import com.example.UniversityManagementSystem.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrolledCourseService enrolledCourseService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private AttendanceService attendanceService;


//--------------------------------------------------------------------------
//---------------------------------------------------------------------------
 //-----------------------------------------------------------------------------
//                        Admin's Dashboard
//-----------------------------------------------------------------------------
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------

    @GetMapping("/dashboard")
    public ModelAndView displayAdminDashboard(){

        ModelAndView modelAndView=new ModelAndView("admin/dashboard.html");
        modelAndView.addObject("departmentCount", departmentService.numberOfDepartmentExist());
        modelAndView.addObject("teacherCount", teacherService.numberOfTeacherExist());
        modelAndView.addObject("studentCount", studentService.numberOfStudentExist());
        modelAndView.addObject("courseCount", courseService.numberOfCourseExist());
        return modelAndView;
    }

//-----------------------------------------------------------------------------
//                        Assign Teacher To course
//-----------------------------------------------------------------------------

    @GetMapping("/assignTeacherToCourse")
    public ModelAndView showSelectedDepartment(Model model){

        ModelAndView modelAndView=new ModelAndView("admin/assignTeacherToCourse.html");
        modelAndView.addObject("departments",departmentService.findAllDepartments());
        return modelAndView;
    }

    @PostMapping("/assignTeacherToCourse")
    public String assignTeacherToCourse(@RequestParam(value="teacher", required = false) Long teacherId,
                                        @RequestParam(value = "course", required = false) String courseCode,
                                        RedirectAttributes redirectAttributes,
                                        Model model){
        model.addAttribute("departments",departmentService.findAllDepartments());

        if(teacherId==null){
            model.addAttribute("errorMessage", "Every field must have to choose value ");
            return "admin/assignTeacherToCourse.html";
        }

        Teacher teacher= teacherService.findTeacherById(teacherId);
        Course course= courseService.findCourseByCourseCode(courseCode);


        boolean updateSuccessfully=teacherService.updateTeachersCourse(teacher, course);
        if(updateSuccessfully){
            redirectAttributes.addFlashAttribute("successMessage", "Success!! Assign Successful");
            return "redirect:/admin/assignTeacherToCourse";


        }

        model.addAttribute("errorMessage", "Failure!! Already that teacher took the course");
        return "admin/assignTeacherToCourse.html";



    }

//---------------------------------------------------------------------------
//                        Get Student Marksheet
//-----------------------------------------------------------------------------

    @GetMapping("/getMarksheetForm")
    public ModelAndView displayFormToSearchMarksheet(Model model){
        ModelAndView modelAndView=new ModelAndView("admin/getMarksheetForm.html");
        return modelAndView;
    }

    @PostMapping("/getMarksheetForm")
    public String redirectToMarksheetByStudentId(@RequestParam(required = false) Long studentId, Model model){
        if(studentId==null){

            model.addAttribute("errorMessage", "Student Id can not be null");
            return "admin/getMarksheetForm.html";

        }
        boolean studentExist= studentService.findStudentbyId(studentId);
        if(!studentExist){
            model.addAttribute("errorMessage", "Student Id do not exist");
            return "admin/getMarksheetForm.html";
        }
        else{
            return "redirect:/admin/getMarksheet?studentId="+studentId;
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
        ModelAndView modelAndView=new ModelAndView("admin/getMarksheet.html");
        Page<EnrolledCourse> marksheetsPage= enrolledCourseService.findMarksheetWithPagination(studentId,currentPage);
        List<EnrolledCourse> marksheets= marksheetsPage.getContent();
        modelAndView.addObject("totalPage",marksheetsPage.getTotalPages());
        modelAndView.addObject("marksheets", marksheets);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("student", studentService.findStudentDetailsbyId(studentId));
        return modelAndView;

    }


//-----------------------------------------------------------------------------
//                        Promoted Student to Next Level Level
//-----------------------------------------------------------------------------
@GetMapping("/promoteAllStudentToNextLevelTerm")
public ModelAndView getPromoteAllStudentToNextLevelTerm(Model model){

    ModelAndView modelAndView=new ModelAndView("admin//promoteAllStudentToNextLevelTerm.html");
    return modelAndView;
}

    @PostMapping("/promoteAllStudentToNextLevelTerm")
    public String promoteAllStudentToNextLevelTerm(Model model,RedirectAttributes redirectAttributes){

        int nextLevel=4,nextTerm=2;
        for(int i=4;i>=1;i--){
            for(int j=2;j>=1;j--){

                if(i==4 && j==2){

                }
                else{

                        List<Student>students= studentService.findAllByLevelAndTerm(i,j);
                        for(Student student:students){

                            ;
                            studentService.updatelevelAndTerm(student,nextLevel,nextTerm);
                            List<Course> courses= courseService.findAllCourseBasedOnDepartmentCodeAndLevelAndTerm(student.getDepartment().getCode(),student.getLevel(),student.getTerm());
                            enrolledCourseService.saveEnrolledCourseDetailWithManyCoursesAndOneStudent(courses, student);

                            lessonService.updateLessonToNewStudentAlsoWithAttendance(student);


                        }



                }
                nextTerm=j;
                nextLevel=i;

            }

        }


            redirectAttributes.addFlashAttribute("successMessage", "Success!! Assign Successful");

        return "redirect:/admin/promoteAllStudentToNextLevelTerm";




    }
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//                        Admin's Department Management Dashboard
//-----------------------------------------------------------------------------
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------

        @GetMapping("/department/dashboard")
        public ModelAndView displayAdminDepartmentDashboard(Model model){

            ModelAndView modelAndView=new ModelAndView("admin/department/dashboard.html");
            modelAndView.addObject("departmentCount", departmentService.numberOfDepartmentExist());
            return modelAndView;
        }

 //-----------------------------------------------------------------------------
//                        get departmentList
//-----------------------------------------------------------------------------

    @GetMapping("/department/getDepartmentList")
    public ModelAndView displayDepartmentListWithPagination(Model model,
                                                            @RequestParam(required = false) Integer currentPage){
        if(currentPage==null){
            currentPage=1;
        }
        ModelAndView modelAndView=new ModelAndView("admin/department/getDepartmentList.html");
        Page<Department> departmentsPage= departmentService.findAllDepartmentsWithPagination(currentPage);
        List<Department> departments=departmentsPage.getContent();
        int totalPage= departmentsPage.getTotalPages();
        modelAndView.addObject("departments", departments);
        modelAndView.addObject("totalPage", totalPage);
        modelAndView.addObject("currentPage", currentPage);


        return modelAndView;
    }

//------------------------------------------------------------------------------------
//                        create Department
//-----------------------------------------------------------------------------

    @GetMapping("/department/create")
    public ModelAndView displayCreateDepartment(@RequestParam(value = "errorMessage", required = false) String errorMessage,
                                                @RequestParam(value = "SuccessMessage", required = false) String successMessage,
                                                Model model){
        Department department= new Department();
        ModelAndView modelAndView= new ModelAndView("admin/department/create.html");
        modelAndView.addObject("department", department);
        return modelAndView;

    }

    @PostMapping("/department/create")
    public String saveCreatedDepartment(@Valid @ModelAttribute("department") Department department,
                                        Errors errors,
                                        Model model,
                                        RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            return "admin/department/create.html";
        }
        boolean errorFlag= departmentService.saveDepartment(department);
        if(errorFlag){
            model.addAttribute("errorMessage","Failure!! Department Already exist");
            return "admin/department/create.html";
        }

        redirectAttributes.addFlashAttribute("successMessage","Department Successfully Created");
        return "redirect:/admin/department/create";
    }


//-----------------------------------------------------------------------------
//                        update Department
//-----------------------------------------------------------------------------

    @GetMapping("/department/update")
    public ModelAndView displayUpdateDepartmentForm(Model model){
        Department department= new Department();
        ModelAndView modelAndView= new ModelAndView("admin/department/update.html");
        modelAndView.addObject("department", department);
        return modelAndView;

    }

    @PostMapping("/department/update")
    public String saveUpdatedDepartment(@ModelAttribute("department") Department department,
                                        Model model,
                                        RedirectAttributes redirectAttributes){
        if(department.getCode().isEmpty()|| department.getCode()==null){
            model.addAttribute("errorMessage","Failure!! Department Code can not be empty");
            return "admin/department/update.html";

        }

        boolean deptExist=departmentService.isDepartmentExist(department);
        if(!deptExist){
            model.addAttribute("errorMessage","Failure!! Department do not exist");
            return "admin/department/update.html";
        }
        boolean success= departmentService.saveUpdatedDepartment(department);
        if(!success){
            model.addAttribute("errorMessage","Failure!! Department can not update");
            return "admin/department/update.html";
        }

        redirectAttributes.addFlashAttribute("successMessage","Department Successfully Created");
        return "redirect:/admin/department/update";
    }

//----------------------------------------------------------------------------------------
//                        delete Department
//-----------------------------------------------------------------------------

    @GetMapping("/department/delete")
    public ModelAndView displayDeleteDepartmentForm(Model model){
        Department department= new Department();
        ModelAndView modelAndView= new ModelAndView("admin/department/delete.html");
        modelAndView.addObject("department", department);
        return modelAndView;

    }

    @PostMapping("/department/delete")
    public String deleteDepartment(@ModelAttribute("department") Department department,
                                        Model model,
                                        RedirectAttributes redirectAttributes){
        if(department.getCode().isEmpty()|| department.getCode()==null){
            model.addAttribute("errorMessage","Failure!! Department Code can not be empty");
            return "admin/department/delete.html";

        }

        boolean deptExist=departmentService.isDepartmentExist(department);
        if(!deptExist){
            model.addAttribute("errorMessage","Failure!! Department do not exist");
            return "admin/department/delete.html";
        }
        boolean success= departmentService.deleteDepartment(department);
        if(!success){
            model.addAttribute("errorMessage","Failure!! Department can not update");
            return "admin/department/delete.html";
        }

        redirectAttributes.addFlashAttribute("successMessage","Department Successfully deleted");
        return "redirect:/admin/department/delete";
    }
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//                         admin's Teacher Management dashboard
//-----------------------------------------------------------------------------
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------


        @GetMapping("/teacher/dashboard")
        public ModelAndView displayAdminTeacherDashboard(Model model){



            ModelAndView modelAndView= new ModelAndView("admin/teacher/dashboard.html");
            modelAndView.addObject("teacherCount", teacherService.numberOfTeacherExist());
            return modelAndView;

        }

//-----------------------------------------------------------------------------
//                        get Teacher List
//-----------------------------------------------------------------------------

    @GetMapping("/teacher/getTeacherList")
    public ModelAndView displayTeacherListWithPagination(Model model,
                                                         @RequestParam(required = false) Integer currentPage){
        if(currentPage==null){
            currentPage=1;
        }
        ModelAndView modelAndView=new ModelAndView("admin/teacher/getTeacherList.html");
        Page<Teacher> teachersPage= teacherService.findTeachersListWithPagination(currentPage);
        List<Teacher> teachers= teachersPage.getContent();
        modelAndView.addObject("totalPage",teachersPage.getTotalPages());
        modelAndView.addObject("teachers", teachers);
        modelAndView.addObject("currentPage", currentPage);
        return modelAndView;

    }

//-----------------------------------------------------------------------------
//                        create Teacher
//-----------------------------------------------------------------------------


        @GetMapping("/teacher/create")
        public ModelAndView displayCreateTeacher(@RequestParam(value = "errorMessage", required = false) String errorMessage,
                                                    @RequestParam(value = "SuccessMessage", required = false) String successMessage,
                                                    Model model){

            Teacher teacher =new Teacher();

            ModelAndView modelAndView= new ModelAndView("admin/teacher/create.html");
            modelAndView.addObject("teacher", teacher);
            modelAndView.addObject("departments",departmentService.findAllDepartments());
            return modelAndView;

        }

        @PostMapping("teacher/create")
        public String saveCreatedTeacher(@Valid @ModelAttribute("teacher") Teacher teacher,
                                            Errors errors,
                                            Model model,
                                            RedirectAttributes redirectAttributes){

            if(errors.hasErrors()){
                model.addAttribute("departments",departmentService.findAllDepartments());
                return "admin/teacher/create.html";
            }
            boolean alreadyExist=teacherService.addTeacherToDatabase(teacher);
            if(alreadyExist){
                model.addAttribute("departments",departmentService.findAllDepartments());
                model.addAttribute("errorMessage","Failure!! Teacher Id Already exist");
                return "admin/teacher/create.html";
            }

            usersService.createUserForTeacher(teacher);



            redirectAttributes.addFlashAttribute("successMessage","Teacher Successfully Created");
            return "redirect:/admin/teacher/create";
        }


//---------------------------------------------------------------------------------------
//                        update Teacher
//-----------------------------------------------------------------------------

    @GetMapping("/teacher/update")
    public ModelAndView displayUpdateTeacherForm( Model model){

        Teacher teacher =new Teacher();

        ModelAndView modelAndView= new ModelAndView("admin/teacher/update.html");
        modelAndView.addObject("teacher", teacher);
        return modelAndView;

    }

    @PostMapping("/teacher/update")
    public String saveUpdatedTeacherInformation(@ModelAttribute("teacher") Teacher teacher,
                                     Model model,
                                     RedirectAttributes redirectAttributes){



        if(teacher.getId()==null)
        {
            model.addAttribute("errorMessage","Failure!! Teacher Id can not be null");
            return "admin/teacher/update.html";
        }
        if(teacher.getPassword()!=null && !teacher.getConfirmPassword().equalsIgnoreCase(teacher.getPassword())){
            model.addAttribute("errorMessage","Failure!! Teacher password and confirm password do not match");
            return "admin/teacher/update.html";

        }
        boolean alreadyExist= teacherService.addUpdatedTeacherToDatabase(teacher);
        if(!alreadyExist){
            model.addAttribute("errorMessage","Failure!! Teacher Id do not exist");
            return "admin/teacher/update.html";
        }
//update pass for user table
        if(teacher.getPassword()!=null && teacher.getConfirmPassword().equalsIgnoreCase(teacher.getPassword())){
            usersService.updateTeacherPassword(teacher);

        }



        redirectAttributes.addFlashAttribute("successMessage","Teacher Successfully updated");
        return "redirect:/admin/teacher/update";
    }


 //-----------------------------------------------------------------------------
//                        delete Teacher
//-----------------------------------------------------------------------------

    @GetMapping("/teacher/delete")
    public ModelAndView displayDeleteTeacherForm(Model model){
        ModelAndView modelAndView=new ModelAndView("admin/teacher/delete.html");
        return modelAndView;
    }

    @PostMapping("/teacher/delete")
    public String deleteTeacherFromDataBase(@RequestParam(required = false) Long teacherId,
                                                 Model model,
                                                 RedirectAttributes redirectAttributes){
        if(teacherId==null){

            model.addAttribute("errorMessage", "Teacher Id can not be null");
            return "admin/teacher/delete.html";

        }
        boolean teacherExist= teacherService.deleteTeacherByTeacherId(teacherId);
        if(!teacherExist){
            model.addAttribute("errorMessage", "Teacher do not exist");
            return "admin/teacher/delete.html";
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Success!! Teacher deleted Successfully");
            return "redirect:/admin/teacher/delete";
        }

    }
    //--------------------------------------------------------------------------
//---------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------
//                        Admin's Student Management Dashboard
//-----------------------------------------------------------------------------
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------


    @GetMapping("/student/dashboard")
    public ModelAndView displayAdminStudentDashboard(Model model){



        ModelAndView modelAndView= new ModelAndView("admin/student/dashboard.html");
        modelAndView.addObject("studentCount", studentService.numberOfStudentExist());
        return modelAndView;

    }

 //------ -------------------------------------------------------------------------------------
//                        getStudent List
//-----------------------------------------------------------------------------


    @GetMapping("/student/getStudentList")
    public ModelAndView displayStudentListWithPagination(Model model,
                                                         @RequestParam(required = false) Integer currentPage){
        if(currentPage==null){
            currentPage=1;
        }
        ModelAndView modelAndView=new ModelAndView("admin/student/getStudentList.html");
        Page<Student> studentsPage= studentService.findStudentsListWithPagination(currentPage);
        List<Student> students= studentsPage.getContent();
        modelAndView.addObject("totalPage",studentsPage.getTotalPages());
        modelAndView.addObject("students", students);
        modelAndView.addObject("currentPage", currentPage);
        return modelAndView;

    }
 //-------------------------------------------------------------------------------------
//                        create Student
//-----------------------------------------------------------------------------
    @GetMapping("/student/create")
    public ModelAndView createStudent(Model model){
        Student student= new Student();
        ModelAndView modelAndView=new ModelAndView("admin/student/create.html");
        modelAndView.addObject("student",student);
        modelAndView.addObject("departments", departmentService.findAllDepartments());

        return modelAndView;
    }

    @PostMapping("/student/create")
    public String addStudentToDatabase(@Valid @ModelAttribute("student") Student student,
                                       Errors errors,
                                       Model model,
                                       RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            model.addAttribute("departments", departmentService.findAllDepartments());
            return "admin/student/create.html";
        }


        boolean alreadyExist= studentService.addStudentToDataBase(student);

        List<Course> courses= courseService.findAllCourseBasedOnDepartmentCodeAndLevelAndTerm(student.getDepartment().getCode(),student.getLevel(),student.getTerm());
        enrolledCourseService.saveEnrolledCourseDetailWithManyCoursesAndOneStudent(courses, student);

        lessonService.updateLessonToNewStudentAlsoWithAttendance(student);





        if(alreadyExist){
            model.addAttribute("departments", departmentService.findAllDepartments());
            model.addAttribute("errorMessage","Failure!! Student Id Already exist");
            return "admin/student/create.html";
        }

        //user creation
        usersService.createUsersForStudent(student);


        redirectAttributes.addFlashAttribute("successMessage", "Success!! Student Created Successfully");

        return "redirect:/admin/student/create";
    }

//-------------------------------------------------------------------------------------
//                        Update Student Info
//-----------------------------------------------------------------------------
    @GetMapping("/student/update")
    public ModelAndView updateStudent(Model model){
        Student student= new Student();
        ModelAndView modelAndView=new ModelAndView("admin/student/update.html");
        modelAndView.addObject("student",student);
        return modelAndView;
    }

    @PostMapping("/student/update")
    public String addUpdatedStudentInfoToDatabase(@ModelAttribute("student") Student student,
                                       Errors errors,
                                       Model model,
                                       RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            return "admin/student/update.html";
        }
        if(student.getId()==null){
            model.addAttribute("errorMessage","Failure!! Student Id must not be blank");
            return "admin/student/update.html";

        }
        if(!student.getConfirmPassword().isEmpty()){
            if(!student.getConfirmPassword().equalsIgnoreCase(student.getPassword())){
                model.addAttribute("errorMessage","Failure!! Student Password And Confirm Password Do not match");
                return "admin/student/update.html";
            }
        }

//        System.out.println("naame ---- > " + student.getName());
//        System.out.println("email ---- > " + student.getEmail());
//        System.out.println("pass ---- > " + student.getPassword());
//        System.out.println("confirm pass ---- > " + student.getConfirmPassword());

        boolean alreadyExist= studentService.addUpdatedStudentInfoToDataBase(student);

        if(!alreadyExist){
            model.addAttribute("errorMessage","Failure!! Student Id Do Not Already exist");
            return "admin/student/update.html";
        }

//if password is type then also update update user password
        if(!student.getPassword().isEmpty()){
            usersService.updateStudentPassword(student);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Success!! Student updated Successfully");

        return "redirect:/admin/student/update";
    }


//-----------------------------------------------------------------------------
//                        delete Student
//-----------------------------------------------------------------------------

    @GetMapping("/student/delete")
    public ModelAndView displayDeleteStudentForm(Model model){
        ModelAndView modelAndView=new ModelAndView("admin/student/delete.html");
        return modelAndView;
    }

    @PostMapping("/student/delete")
    public String deleteStudentByStudentId(@RequestParam(required = false) Long studentId,
                                                 Model model,
                                                 RedirectAttributes redirectAttributes){
        if(studentId==null){

            model.addAttribute("errorMessage", "Student Id can not be null");
            return "admin/student/delete.html";

        }
        boolean studentExist= studentService.deleteStudentByStudentId(studentId);
        if(!studentExist){
            model.addAttribute("errorMessage", "Student do not exist");
            return "admin/student/delete.html";
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Success!! Student deleted Successfully");
            return "redirect:/admin/student/delete";
        }

    }

    //--------------------------------------------------------------------------
//---------------------------------------------------------------------------
//------------------------------------------------------------------------------
//                        Admin's Course Management Dashboard
//-----------------------------------------------------------------------------
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------


    @GetMapping("/course/dashboard")
    public ModelAndView displayAdminCourseDashboard(Model model){



        ModelAndView modelAndView= new ModelAndView("admin/course/dashboard.html");
        modelAndView.addObject("courseCount", courseService.numberOfCourseExist());
        return modelAndView;

    }
//---------------------------------------------------------------------------
//                         Course get list Dashboard
//-----------------------------------------------------------------------------
    @GetMapping("/course/getCourseList")
    public ModelAndView displayCourseListWithPagination(Model model,
                                                        @RequestParam(required = false) Integer currentPage){
        if(currentPage==null){
            currentPage=1;
        }
        ModelAndView modelAndView=new ModelAndView("admin/course/getCourseList.html");
        Page<Course> coursesPage= courseService.findCoursesListWithPagination(currentPage);
        List<Course> courses= coursesPage.getContent();
        modelAndView.addObject("totalPage",coursesPage.getTotalPages());
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("currentPage", currentPage);
        return modelAndView;

    }
//-----------------------------------------------------------------------------
//                        create Course
//-----------------------------------------------------------------------------

    @GetMapping("/course/create")
    public ModelAndView createCourse(Model model){
        Course course= new Course();
        ModelAndView modelAndView=new ModelAndView("admin/course/create.html");
        modelAndView.addObject("course",course);
        modelAndView.addObject("departments", departmentService.findAllDepartments());

        return modelAndView;
    }

    @PostMapping("/course/create")
    public String addCourseToDatabase(@Valid @ModelAttribute("course") Course course,
                                       Errors errors,
                                       Model model,
                                       RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            model.addAttribute("departments", departmentService.findAllDepartments());
            return "admin/course/create.html";
        }

        boolean alreadyExist= courseService.addCourseToDataBase(course);

        List<Student> students= studentService.findStudentDetailsbyDepartmentCodeAndLevelAndTerm(course.getDepartment().getCode(),course.getLevel(),course.getTerm());

        enrolledCourseService.saveEnrolledCourseDetailWithOneCoursesToManyStudent(students, course);

        if(alreadyExist){
            model.addAttribute("departments", departmentService.findAllDepartments());
            model.addAttribute("errorMessage","Failure!! Course Id Already exist");
            return "admin/course/create.html";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Success!! Course Created Successfully");

        return "redirect:/admin/course/create";
    }


//-----------------------------------------------------------------------------
//                        Update Course
//-----------------------------------------------------------------------------

    @GetMapping("/course/update")
    public ModelAndView updateCourse(Model model){
        ModelAndView modelAndView=new ModelAndView("admin/course/update.html");
        return modelAndView;
    }

    @PostMapping("/course/update")
    public String addUpdateCourseToDatabase(@RequestParam(required = false) String courseCode,
                                      @RequestParam(required = false) Long teacherRemains,
                                      @RequestParam(required = false) String courseName,
                                            @RequestParam(required = false) float courseCredit,
                                      Model model,
                                      RedirectAttributes redirectAttributes){

      if(courseCode.isEmpty() || courseCode==null){
          model.addAttribute("errorMessage","Failure!! Course Code Can not be Blank");
          return "admin/course/update.html";
      }



        boolean courseExist= courseService.addUpdateCourseToDataBase(courseCode, teacherRemains, courseName, courseCredit);


        if(!courseExist){
            model.addAttribute("errorMessage","Failure!! Course Does not Exist");
            return "admin/course/update.html";
        }


        redirectAttributes.addFlashAttribute("successMessage", "Success!! Course updated Successfully");

        return "redirect:/admin/course/update";
    }


//-----------------------------------------------------------------------------
//                        delete course
//-----------------------------------------------------------------------------

    @GetMapping("/course/delete")
    public ModelAndView displayDeleteCourse(Model model){
        ModelAndView modelAndView=new ModelAndView("admin/course/delete.html");
        return modelAndView;
    }

    @PostMapping("/course/delete")
    public String redirectToMarksheetByStudentId(@RequestParam(required = false) String courseCode,
                                                 Model model,
                                                 RedirectAttributes redirectAttributes){
        if(courseCode==null || courseCode.isEmpty()){

            model.addAttribute("errorMessage", "Student Id can not be null");
            return "admin/course/delete.html";

        }
        boolean courseExist= courseService.deleteCourseByCourseCode(courseCode);
        if(!courseExist){
            model.addAttribute("errorMessage", "Course Code do not exist");
            return "admin/course/delete.html";
        }
        else{
            redirectAttributes.addFlashAttribute("successMessage", "Success!! Course deleted Successfully");
            return "redirect:/admin/course/delete";
        }

    }





}