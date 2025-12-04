package com.marketplace.annotation;

import com.marketplace.validator.RgValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RgValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RgValido {
    String message() default "RG inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
