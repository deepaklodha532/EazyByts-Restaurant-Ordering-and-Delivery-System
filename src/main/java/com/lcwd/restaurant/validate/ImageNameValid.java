package com.lcwd.restaurant.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNamveValidate.class)
public @interface ImageNameValid {
    String message() default "Invalid image ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
