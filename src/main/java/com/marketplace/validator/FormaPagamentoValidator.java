package com.marketplace.validator;

import com.marketplace.annotation.FormaPagamentoValida;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

import static com.marketplace.model.enums.FormaPagamento.*;

public class FormaPagamentoValidator implements ConstraintValidator<FormaPagamentoValida, String> {

    Set<String> formaPagamentoValidas = Set.of(
            CARTAO_DEBITO.toString(),
            CARTAO_CREDITO.toString(),
            BOLETO.toString(),
            PIX.toString()
    );

    @Override
    public boolean isValid(String formaPagamento, ConstraintValidatorContext constraintValidatorContext) {
        return formaPagamentoValidas.contains(formaPagamento);
    }
}
