package com.marketplace.validator;

import com.marketplace.annotation.PedidoStatusValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

import static com.marketplace.model.enums.PedidoStatus.*;


public class PedidoStatusValidator implements ConstraintValidator<PedidoStatusValido, String> {

    Set<String> pedidoStatusValidos = Set.of(
            CONFIRMADO.toString(),
            EM_PREPARACAO.toString(),
            ENVIADO.toString(),
            ENTREGUE.toString(),
            CANCELADO.toString()
    );

    @Override
    public boolean isValid(String pedidoStatus, ConstraintValidatorContext constraintValidatorContext) {
        return pedidoStatusValidos.contains(pedidoStatus);
    }
}
