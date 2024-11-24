package com.devteria.identity_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) // đối tượng validate
@Retention(RetentionPolicy.RUNTIME) // thời điểm validate
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobContraint {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
