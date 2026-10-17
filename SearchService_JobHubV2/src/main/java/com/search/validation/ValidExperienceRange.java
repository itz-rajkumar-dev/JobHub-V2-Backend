package com.search.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ExperienceRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExperienceRange {

    String message() default "Minimum experience cannot be greater than maximum experience";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}