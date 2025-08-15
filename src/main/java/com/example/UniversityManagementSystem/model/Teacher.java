package com.example.UniversityManagementSystem.model;

import com.example.UniversityManagementSystem.annotation.FieldValueMatch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldValueMatch(field = "password", fieldMatch = "confirmPassword",message = "Password does not match")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Teacher extends BaseEntity{

    @Id
    @Positive(message = "Teacher Id is positive number")
     private Long id;

    @NotBlank(message = "Name can not be blank")
    @Pattern(regexp = "^[A-Za-z .]+$", message = "Name must contain only letters, spaces and dot")
    @Size(min = 2, message = "Name must be min 2 character")
    private String name;

    @NotBlank(message = "Designation can not be blank")
    @Pattern(regexp = "^[A-Za-z .]+$", message = "Designation must contain only letters, spaces and dot")
    @Size(min = 2, message = "Designation must be min 2 character")
    private String designation;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, message = "Password must be min 6 character")
    private String password;

    @Transient
    private String confirmPassword;

    @NotBlank(message = "Email can not be blank")
    @Email
    private String email;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department department;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Course> courses=new HashSet<>();

    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    Users users;


}
