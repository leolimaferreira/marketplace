package com.marketplace.service;

import com.marketplace.dto.loja.LojaComDonoExistenteDTO;
import com.marketplace.dto.loja.LojaComDonoNovoDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.mapper.LojaMapper;
import com.marketplace.model.Loja;
import com.marketplace.repository.LojaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final DonoService donoService;
    private final LojaRepository lojaRepository;
    private final LojaMapper lojaMapper;

    @Transactional
    public LojaRespostaDTO criarLojaComDonoNovo(LojaComDonoNovoDTO dto) {
        Loja loja = lojaMapper.mapearParaLoja(dto);
        donoService.vincularDonoALoja(dto.dono(), loja);
        Loja lojaSalva = lojaRepository.save(loja);
        return lojaMapper.mapearParaLojaResposta(lojaSalva);
    }

    @Transactional
    public LojaRespostaDTO criarLojaComDonoExistente(LojaComDonoExistenteDTO dto) {
        Loja loja = lojaMapper.mapearParaLoja(dto);
        donoService.vincularDonoALoja(dto.emailDono(), loja);
        Loja lojaSalva = lojaRepository.save(loja);
        return lojaMapper.mapearParaLojaResposta(lojaSalva);
    }
}
