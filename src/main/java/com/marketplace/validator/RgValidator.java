package com.marketplace.validator;

import com.marketplace.annotation.RgValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RgValidator implements ConstraintValidator<RgValido, String> {

    @Override
    public void initialize(RgValido constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String rg, ConstraintValidatorContext context) {
        if (rg == null || rg.isBlank()) {
            return false;
        }

        String rgLimpo = rg.replaceAll("[^0-9Xx]", "");

        if (rgLimpo.length() < 7 || rgLimpo.length() > 13) {
            return false;
        }

        if (rgLimpo.matches("(\\d)\\1{6,}")) {
            return false;
        }

        return rgLimpo.matches("[0-9Xx]+");
    }

}
