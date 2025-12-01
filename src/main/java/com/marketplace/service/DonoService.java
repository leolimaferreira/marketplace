package com.marketplace.service;

import com.marketplace.dto.usuario.dono.DonoCriacaoDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.DonoMapper;
import com.marketplace.model.Dono;
import com.marketplace.model.Loja;
import com.marketplace.repository.DonoRepository;
import com.marketplace.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonoService {

    private final DonoRepository donoRepository;
    private final DonoMapper donoMapper;
    private final UsuarioValidator usuarioValidator;

    public void vincularDonoALoja(Object dono, Loja loja) {
        if (dono instanceof String string) {
            donoRepository.findByEmailAndAtivo(string)
                .ifPresentOrElse(
                        loja::setDono,
                    () -> {
                        throw new NaoEncontradoException("Dono não encontrado ou inativo.");
                    }
                );
        } else {
            DonoCriacaoDTO donoDTO = (DonoCriacaoDTO) dono;
            Dono novoDono = donoMapper.mapearParaDono(donoDTO);
            usuarioValidator.validarEmail(novoDono);
            donoRepository.save(novoDono);
            loja.setDono(novoDono);
        }
    }
}
