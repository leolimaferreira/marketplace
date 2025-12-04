package com.marketplace.validator;

import com.marketplace.annotation.CnpjValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CnpjValidator implements ConstraintValidator<CnpjValido, String> {

    @Override
    public void initialize(CnpjValido constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.isBlank()) {
            return false;
        }

        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int primeiroDigito = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        if (Character.getNumericValue(cnpj.charAt(12)) != primeiroDigito) {
            return false;
        }

        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        int segundoDigito = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        return Character.getNumericValue(cnpj.charAt(13)) == segundoDigito;
    }
}
