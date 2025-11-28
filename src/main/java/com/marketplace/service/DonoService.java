package com.marketplace.service;

import com.marketplace.dto.usuario.dono.DonoCriacaoDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.DonoMapper;
import com.marketplace.model.Dono;
import com.marketplace.model.Loja;
import com.marketplace.repository.DonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonoService {

    private final DonoRepository donoRepository;
    private final DonoMapper donoMapper;

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
            donoRepository.save(novoDono);
            loja.setDono(novoDono);
        }
    }
}
