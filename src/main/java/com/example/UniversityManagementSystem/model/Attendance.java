package com.example.UniversityManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Attendance extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attendanceTaker;

    private Long presentStatus;
    //0 unmarked
    //1 present
    //2 absent


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lesson lesson;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EnrolledCourse enrolledCourse;


}
