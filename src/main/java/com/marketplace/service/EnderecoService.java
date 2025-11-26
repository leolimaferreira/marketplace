package com.marketplace.service;

import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import com.marketplace.dto.endereco.EnderecoRespostaDTO;
import com.marketplace.mapper.EnderecoMapper;
import com.marketplace.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoRespostaDTO criarEndereco(EnderecoCriacaoDTO dto, UUID clienteId) {
        return enderecoMapper.mapearParaEnderecoRespostaDTO(enderecoRepository.save(enderecoMapper.mapearParaEndereco(dto, clienteId)));
    }
}
