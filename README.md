# University Management System

A Spring Boot-based web application to manage university operations such as student and teacher management, course creation, attendance tracking, and marksheet viewing. Built using **Spring Boot**, **Spring Data JPA**, **Thymeleaf**, and **MySQL**.


---

## Features

Total 8 semister in the university management system:
- Level 1 Term 1
- Level 1 Term 2
- Level 2 Term 1
- Level 2 Term 2
- Level 3 Term 1
- Level 3 Term 2
- Level 4 Term 1
- Level 4 Term 2

Each semister course will automatycally assigned to that level term student.

### **Admin**
- Create, update and delete of department.
- Create, update and delete of student.
- Create, update and delete of teacher.
- Create, update and delete of course.
- Assign teacher to course.
- View statistics (total departments, teachers, students, course).
- Promote all students.

### **Teacher**
- Create, update and delete lessons for assigned courses.
- Take attendance for students in each lesson.
- Assign course marks to students.

### **Student**
- View attendance for each course.
- View marksheet.



---

## Technologies Used
- **Backend:** Spring Boot, Spring Data JPA, Hibernate
- **Frontend:** Thymeleaf, Bootstrap, HTML, CSS, JavaScript
- **Database:** MySQL
- **IDE:** IntelliJ IDEA
- **Build Tool:** Maven
- **Version Control:** Git & GitHub

---

## Database Design & Relationships

### **Main Entities**
- Department
- Teacher
- Student
- Course
- Lesson
- Attendance
- EnrolledCourse
- Role
- Users

### **Relationships**

**One-to-One:**
- Users ↔ Teacher
- Users ↔ Student

**One-to-Many:**
- Department ↔ Teacher
- Department ↔ Student
- Department ↔ Course
- Lesson ↔ Attendance
- Course ↔ EnrolledCourse
- Student ↔ EnrolledCourse
- EnrolledCourse ↔ Lesoon
- Course ↔ Lesson

**Many-to-Many:** Teacher ↔ Course

**Many-to-One:**
- Users → Role

---


## Installation & Setup

### **Prerequisites**
- Java 17+ (recomemended 21)
- Maven
- MySQL
- IntelliJ IDEA (recommended)

### **Steps**
1. **Clone the repository:**
   ```bash
   git clone https://github.com/AnamulHasanRony/UniversityManagementSystem.git
   cd UniversityManagementSystem
   ```

2. **Create MySQL database:**
   ```sql
   CREATE DATABASE university_management_system;
   ```

3. **Update `src/main/resources/application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/university_management_system
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Build and run:**
   ```bash
   mvn spring-boot:run
   ```
Or run UniversityManagementSystemApplication from your IDE

6. **Many-to-Many Foreign Key Setup:**

When Spring Boot creates the `teacher_courses` join table for **Teacher ↔ Course**, it creates foreign keys automatically.

However, **on delete cascade** is not enabled by default for many-to-many join tables in JPA.  
So you need to manually enforce cascade delete in MySQL by running the bellow sql query, run:

```sql
ALTER TABLE teacher_courses
DROP FOREIGN KEY FK3yob9rjrxc41o4wxwcfqneahv,
ADD CONSTRAINT FK3yob9rjrxc41o4wxwcfqneahv
FOREIGN KEY (teachers_id)
REFERENCES teacher(id)
ON DELETE CASCADE;

ALTER TABLE teacher_courses
DROP FOREIGN KEY FKf23swa2q6cn6rhcfux6xahoew,
ADD CONSTRAINT FKf23swa2q6cn6rhcfux6xahoew
FOREIGN KEY (courses_code)
REFERENCES course(code)
ON DELETE CASCADE;
```

7. **Build and run:**
   ```bash
   mvn spring-boot:run
   ```
Or run UniversityManagementSystemApplication from your IDE


## Default
### Default URL
| Role    | URL                     |
| ------- | ----------------------- |
| Admin   |    `/admin/dashboard`   |
| Teacher |    `/teacher/dashboard` |
| Student |    `/student/dashboard` |
| App     | `http://localhost:8080` |

### Default Admin Password
**username: admin
password: admin**

### Default username format for student and teacher
fot student username -->  student_id
Example: student_1804118

fot teacher username --> teacher_id
Example: teacher_18118




<img src="\src\main\resources\static\assets\images\readMeImage\ERDiagram.png" alt="Project Screenshot" width="500">
