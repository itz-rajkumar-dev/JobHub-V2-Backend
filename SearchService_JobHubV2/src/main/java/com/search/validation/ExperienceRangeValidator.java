package com.search.validation;

import com.search.dto.SearchRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExperienceRangeValidator
        implements ConstraintValidator<ValidExperienceRange, SearchRequestDto> {

    @Override
    public boolean isValid(
            SearchRequestDto requestDto,
            ConstraintValidatorContext context) {

        if (requestDto == null) {
            return true;
        }

        Integer minExperience = requestDto.getMinExperience();
        Integer maxExperience = requestDto.getMaxExperience();

        // Agar dono me se koi ek nahi diya hai
        if (minExperience == null || maxExperience == null) {
            return true;
        }

        // minExperience <= maxExperience hona chahiye
        return minExperience <= maxExperience;
    }
}