package com.marketplace.validator;

import com.marketplace.exception.ConflitoException;
import com.marketplace.model.Loja;
import com.marketplace.repository.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LojaValidator {

    private final LojaRepository lojaRepository;

    public void validarCnpj(Loja loja) {
        if (existeLojaComMesmoCnpj(loja)) {
            throw new ConflitoException("Já existe uma loja com esse cnpj cadastrada");
        }
    }

    private boolean existeLojaComMesmoCnpj(Loja loja) {
        Optional<Loja> lojaEncontrada = lojaRepository.findByCnpjAndAtivo(loja.getCnpj());

        if (loja.getId() == null) {
            return lojaEncontrada.isPresent();
        }
        return lojaEncontrada.isPresent() && !loja.getId().equals(lojaEncontrada.get().getId());
    }
}
