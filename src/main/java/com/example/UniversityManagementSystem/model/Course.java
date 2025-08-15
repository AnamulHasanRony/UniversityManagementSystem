package com.example.UniversityManagementSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "code"
//)
public class Course extends BaseEntity{

    @Id
    @Pattern(regexp = "^[A-Za-z0-9- ]+$", message = "Course code must contain only letters, spaces and dot")
    @Size(min = 2, message = "Course code must be min 2 character")
    private String code;

    @NotBlank(message = "Course Name can not be blank")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Course Name must contain only letters, spaces and dot")
    @Size(min = 2, message = "Course Name must be min 2 character")
    private String name;

    @Positive
    private float credit;

    private int level;
    private int term;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department department;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Teacher> teachers= new HashSet<>();


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EnrolledCourse> enrolledCourses=new ArrayList<>();


    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Lesson> lessons=new ArrayList<>();


}
