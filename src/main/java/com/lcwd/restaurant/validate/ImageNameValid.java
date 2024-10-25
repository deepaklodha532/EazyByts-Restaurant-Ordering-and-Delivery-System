package com.lcwd.restaurant.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.lang.reflect.Field;

@Target({ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //error message
    String message() default  "Invalid image Name !!"  ;

    // represent group of constraints
    Class<?>[] groups() default { } ;

    //additional information
    Class<? extends Payload> [] payload() default { } ;

}
