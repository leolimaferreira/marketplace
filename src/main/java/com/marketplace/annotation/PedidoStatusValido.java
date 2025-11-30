package com.marketplace.annotation;

import com.marketplace.validator.PedidoStatusValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PedidoStatusValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface PedidoStatusValido {
    String message() default "Status do pedido deve ser 'CONFIRMADO',  'EM_PREPARACAO', 'ENVIADO', 'ENTREGUE' ou 'CANCELADO'.";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
