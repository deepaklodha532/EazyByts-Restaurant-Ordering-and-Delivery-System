package com.lcwd.restaurant.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@
public class ImageNameValidator implements ConstraintValidator<ImageNameValid , String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}
