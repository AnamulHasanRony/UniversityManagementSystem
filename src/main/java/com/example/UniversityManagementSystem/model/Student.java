package com.example.UniversityManagementSystem.model;

import com.example.UniversityManagementSystem.annotation.FieldValueMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldValueMatch(field = "password", fieldMatch = "confirmPassword",message = "Password does not match")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Student extends BaseEntity {

    @Id
    @Positive(message = "Teacher Id is positive number")
    private Long id;

    @NotBlank(message = "Name can not be blank")
    @Pattern(regexp = "^[A-Za-z .]+$", message = "Name must contain only letters, spaces and dot")
    @Size(min = 2, message = "Name must be min 2 character")
    private String name;


    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, message = "Password must be min 6 character")
    private String password;

    @Transient
    private String confirmPassword;

    @Email(message = "Email must be a email")
    private String email;

    private int level;
    private int term;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department department;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EnrolledCourse> enrolledCourses=new ArrayList<>();


    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    Users users;


}
