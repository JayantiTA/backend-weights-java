package com.example.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxGreaterThanOrEqualMinValidator.class)
@Documented
public @interface MaxGreaterThanOrEqualMin {
    String message() default "Maximum input must be greater than or equal to minimum input";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


    // Add an attribute to specify the DTO classes that should be validated
    Class<?>[] value();
}