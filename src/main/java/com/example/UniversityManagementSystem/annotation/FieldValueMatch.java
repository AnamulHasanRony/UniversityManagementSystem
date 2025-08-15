package com.example.UniversityManagementSystem.annotation;


import com.example.UniversityManagementSystem.validator.FieldValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = FieldValueMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldValueMatch {
    String message() default "{Field Value does not match}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

     String field();
     String fieldMatch();

//    @Target(ElementType.TYPE)
//    @Retention(RetentionPolicy.RUNTIME)
//     @interface List {
//        FieldValueMatch[] value();
//    }

}
