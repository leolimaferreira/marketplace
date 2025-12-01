package com.marketplace.service;

import com.marketplace.dto.loja.LojaAtualizacaoDTO;
import com.marketplace.dto.loja.LojaComDonoExistenteDTO;
import com.marketplace.dto.loja.LojaComDonoNovoDTO;
import com.marketplace.dto.loja.LojaRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.LojaMapper;
import com.marketplace.model.Loja;
import com.marketplace.repository.LojaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.marketplace.utils.Constantes.LOJA_NAO_ENCONTRADA;

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

    @Transactional(readOnly = true)
    public List<LojaRespostaDTO> listarLojasAtivas() {
        return lojaRepository.findAllAtivo().stream()
                .map(lojaMapper::mapearParaLojaResposta)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LojaRespostaDTO> listarTodasLojas() {
        return lojaRepository.findAll().stream()
                .map(lojaMapper::mapearParaLojaResposta)
                .toList();
    }

    @Transactional
    public LojaRespostaDTO atualizarLoja(UUID id, LojaAtualizacaoDTO dto) {
        Loja loja = lojaRepository.findByIdAndAtivo(id)
                .orElseThrow(() -> new NaoEncontradoException(LOJA_NAO_ENCONTRADA));

        lojaMapper.atualizarLoja(loja, dto);

        return lojaMapper.mapearParaLojaResposta(lojaRepository.save(loja));
    }
}
