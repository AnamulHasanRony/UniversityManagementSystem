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
| Entity             | Description                           |
| ------------------ | ------------------------------------- |
| **Department**     | Represents a university department.   |
| **Teacher**        | Represents a faculty member.          |
| **Student**        | Represents a student in the university. |
| **Course**         | Represents a subject or course taught. |
| **Lesson**         | Represents an individual course lesson|
| **Attendance**     | Tracks student presence for each lesson. |
| **EnrolledCourse** | Tracks student enrollment in courses. |
| **Role**           | Represents a role (e.g., Admin, Teacher, Student). |
| **Users**          | Represents a system user account with login access. |


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

## Data Base ER Diagram

<img src="\src\main\resources\static\assets\images\readMeImage\ERDiagram.png" alt="Project Screenshot" width="500">


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
**username: admin**

**password: admin**

### Default username format for student and teacher
**Student** 

- username: student_id

- Example: student_1804118

**Teacher**

- username: teacher_id

- Example: teacher_18118


## Sample Web Pages

<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(48).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(49).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(50).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(51).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(52).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(53).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(54).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(55).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(56).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(57).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(58).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(59).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(60).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(61).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(62).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(63).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(64).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(65).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(66).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(67).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(68).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(69).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(70).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(71).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(72).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(77).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(78).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(80).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(81).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(82).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(83).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(84).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(85).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(86).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(87).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(88).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(89).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(90).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(91).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(92).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(93).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(94).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(95).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(96).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(97).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(98).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(99).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(100).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(101).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(102).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(103).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(104).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(105).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(106).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(107).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(108).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(109).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(110).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(111).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(112).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(113).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(114).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(115).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(116).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(117).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(118).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(119).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(120).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(121).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(122).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(123).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(124).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(125).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(127).png" width="300">
<img src="src/main/resources/static/assets/images/readMeImage/Screenshot%20(128).png" width="300">


