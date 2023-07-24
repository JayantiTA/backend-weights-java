package com.example.backend.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaxGreaterThanOrEqualMinValidator implements ConstraintValidator<MaxGreaterThanOrEqualMin, Object> {

    private Class<?>[] targetClasses;

    @Override
    public void initialize(MaxGreaterThanOrEqualMin constraintAnnotation) {
        targetClasses = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true; // Null values are handled by @NotNull or @NotEmpty annotations
        }

        // Check if the DTO class is one of the specified target classes
        for (Class<?> targetClass : targetClasses) {
            if (targetClass.isInstance(dto)) {
                // Perform the validation logic
                Integer minValue = invokeGetter(dto, "getMin");
                Integer maxValue = invokeGetter(dto, "getMax");
                if (maxValue != null && minValue != null && maxValue >= minValue) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        // Return true for other classes (not one of the specified target classes)
        return true;
    }

    private Integer invokeGetter(Object dto, String getterName) {
        try {
            Method getterMethod = dto.getClass().getMethod(getterName);
            return (Integer) getterMethod.invoke(dto);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error(getterName, e);
            e.printStackTrace();
            // Return a default value in case of an exception
            return null;
        }
    }
}

