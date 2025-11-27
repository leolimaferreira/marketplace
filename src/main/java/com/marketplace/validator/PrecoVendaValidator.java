package com.marketplace.validator;

import com.marketplace.annotation.PrecoVendaValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class PrecoVendaValidator implements ConstraintValidator<PrecoVendaValido, Object> {

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context) {
        try {
            Field precoCompraField = dto.getClass().getDeclaredField("precoCompra");
            Field precoVendaField = dto.getClass().getDeclaredField("precoVenda");

            BigDecimal precoCompra = (BigDecimal) precoCompraField.get(dto);
            BigDecimal precoVenda = (BigDecimal) precoVendaField.get(dto);

            if (precoCompra == null || precoVenda == null) {
                return true;
            }

            return precoVenda.compareTo(precoCompra) > 0;

        } catch (Exception e) {
            return false;
        }
    }
}
