package com.marketplace.annotation;

import com.marketplace.validator.PrecoVendaValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PrecoVendaValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrecoVendaValido {
    String message() default "Preço de venda deve ser maior que o preço de compra";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
