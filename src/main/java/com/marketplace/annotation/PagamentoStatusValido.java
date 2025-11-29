package com.marketplace.annotation;

import com.marketplace.validator.PagamentoStatusValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PagamentoStatusValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PagamentoStatusValido {
    String message() default "Status de pagamento deve ser 'PENDENTE',  'CONCLUIDO' ou 'CANCELADO'";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}