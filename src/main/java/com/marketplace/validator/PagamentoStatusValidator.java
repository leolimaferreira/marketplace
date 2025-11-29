package com.marketplace.validator;

import com.marketplace.annotation.PagamentoStatusValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

import static com.marketplace.model.enums.PagamentoStatus.*;

public class PagamentoStatusValidator implements ConstraintValidator<PagamentoStatusValido, String> {

    Set<String> pagamentoStatusValidos = Set.of(
            PENDENTE.toString(),
            CONCLUIDO.toString(),
            CANCELADO.toString()
    );

    @Override
    public boolean isValid(String pagamentoStatus, ConstraintValidatorContext constraintValidatorContext) {
        return pagamentoStatusValidos.contains(pagamentoStatus);
    }
}
