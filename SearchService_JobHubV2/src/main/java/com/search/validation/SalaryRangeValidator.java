package com.search.validation;

import com.search.dto.SearchRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class SalaryRangeValidator
        implements ConstraintValidator<ValidSalaryRange, SearchRequestDto> {

    @Override
    public boolean isValid(
            SearchRequestDto requestDto,
            ConstraintValidatorContext context) {

        if (requestDto == null) {
            return true;
        }

        BigDecimal minSalary = requestDto.getMinSalary();
        BigDecimal maxSalary = requestDto.getMaxSalary();

        // Agar dono me se koi ek nahi diya hai
        if (minSalary == null || maxSalary == null) {
            return true;
        }

        // minSalary <= maxSalary hona chahiye
        return minSalary.compareTo(maxSalary) <= 0;
    }
}