package com.marketplace.annotation;

import com.marketplace.validator.FormaPagamentoValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FormaPagamentoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FormaPagamentoValida {
    String message() default "Forma de pagamento deve ser 'CARTAO_DEBITO',  'CARTAO_CREDITO', 'BOLETO' ou 'PIX'";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
