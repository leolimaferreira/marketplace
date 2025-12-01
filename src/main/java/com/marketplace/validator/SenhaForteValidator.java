package com.marketplace.validator;

import com.marketplace.annotation.SenhaForte;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class SenhaForteValidator implements ConstraintValidator<SenhaForte, String> {

    private static final String SENHA_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,}).*$";

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext constraintValidatorContext) {
        if (senha == null) return true;
        return senha.matches(SENHA_PATTERN);
    }
}