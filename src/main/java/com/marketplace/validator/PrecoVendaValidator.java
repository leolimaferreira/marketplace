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

            precoCompraField.setAccessible(true);
            precoVendaField.setAccessible(true);

            BigDecimal precoCompra = (BigDecimal) precoCompraField.get(dto);
            BigDecimal precoVenda = (BigDecimal) precoVendaField.get(dto);

            if (precoCompra == null && precoVenda == null) {
                return true;
            }

            if (precoCompra == null || precoVenda == null) {
                return true;
            }

            return precoVenda.compareTo(precoCompra) > 0;

        } catch (NoSuchFieldException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
