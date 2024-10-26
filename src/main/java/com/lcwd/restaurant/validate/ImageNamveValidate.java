package com.lcwd.restaurant.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNamveValidate implements ConstraintValidator<ImageNameValid ,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if(s.isBlank())
            return false ;
        else
             return true;
    }
}
