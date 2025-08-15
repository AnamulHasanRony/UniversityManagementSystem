package com.example.UniversityManagementSystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "code"
//)
public class Department {

    @Id
    @NotBlank
    @Size(min = 2, message = "Department Code must be min 2 characters")
    private String code;

    @NotBlank
    @Size(min = 2, message = "Department name must be min 2 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Course Name must contain only letters, spaces and dot")
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Student> students =new ArrayList<>();



    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Teacher> teachers = new ArrayList<>();


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();
}
