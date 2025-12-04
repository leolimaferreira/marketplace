package com.marketplace.validator;

import com.marketplace.annotation.CepValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<CepValido, String> {

    @Override
    public void initialize(CepValido constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null || cep.isBlank()) {
            return false;
        }

        String cepNumerico = cep.replaceAll("\\D", "");

        if (cepNumerico.length() != 8) {
            return false;
        }

        if (cepNumerico.matches("(\\d)\\1{7}")) {
            return false;
        }

        return cep.matches("\\d{5}-?\\d{3}");
    }
}
