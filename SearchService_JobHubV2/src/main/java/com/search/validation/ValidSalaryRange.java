package com.search.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SalaryRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSalaryRange {

    String message() default "Minimum salary cannot be greater than maximum salary";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}