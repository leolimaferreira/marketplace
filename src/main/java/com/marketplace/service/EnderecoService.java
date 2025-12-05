package com.marketplace.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.marketplace.dto.endereco.EnderecoAtualizacaoDTO;
import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import com.marketplace.dto.endereco.EnderecoRespostaDTO;
import com.marketplace.exception.NaoAutorizadoException;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.EnderecoMapper;
import com.marketplace.model.Endereco;
import com.marketplace.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.marketplace.utils.Constantes.ADMIN;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Transactional
    public void criarEndereco(EnderecoCriacaoDTO dto, UUID clienteId) {
        enderecoMapper.mapearParaEnderecoRespostaDTO(enderecoRepository.save(enderecoMapper.mapearParaEndereco(dto, clienteId)));
    }

    @Transactional
    public List<EnderecoRespostaDTO> adicionarEndereco(List<EnderecoCriacaoDTO> enderecos, UUID clienteId, String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        if (!decodedJWT.getSubject().equals(clienteId.toString()) && !decodedJWT.getClaim("role").toString().equals(ADMIN)) {
            throw new NaoAutorizadoException("Somente o proprietário da conta ou o admin podem adicionar endereços");
        }

        return enderecos.stream()
                .map(enderecoDTO -> {
                    enderecoRepository.findPrincipalByClienteId(clienteId).ifPresent(endereco -> {
                        if (Boolean.TRUE.equals(enderecoDTO.principal())) {
                            endereco.setPrincipal(false);
                            enderecoRepository.save(endereco);
                        }
                    });
                    Endereco endereco = enderecoMapper.mapearParaEndereco(enderecoDTO, clienteId);
                    Endereco salvo = enderecoRepository.save(endereco);
                    return enderecoMapper.mapearParaEnderecoRespostaDTO(salvo);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<EnderecoRespostaDTO> listarEnderecosPorCliente(UUID clienteId, String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        if (!decodedJWT.getSubject().equals(clienteId.toString()) && !decodedJWT.getClaim("role").toString().equals(ADMIN)) {
            throw new NaoAutorizadoException("Somente o proprietário da conta ou o admin podem ver os endereços");
        }

        List<Endereco> enderecos = enderecoRepository.findAllByClienteId(clienteId);
        return enderecos.stream()
                .map(enderecoMapper::mapearParaEnderecoRespostaDTO)
                .toList();
    }

    @Transactional
    public EnderecoRespostaDTO atualizarEndereco(UUID id, EnderecoAtualizacaoDTO dto, String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Endereço não encontrado"));

        if (!decodedJWT.getSubject().equals(endereco.getCliente().getId().toString()) && !decodedJWT.getClaim("role").toString().equals(ADMIN)) {
            throw new NaoAutorizadoException("Somente o proprietário da conta ou o admin podem atualizar o endereço");
        }

        if (Boolean.TRUE.equals(dto.principal())) {
            enderecoRepository.findPrincipalByClienteId(endereco.getCliente().getId()).ifPresent(enderecoPrincipal -> {
                enderecoPrincipal.setPrincipal(false);
                enderecoRepository.save(enderecoPrincipal);
            });
        }

        enderecoMapper.atualizarEndereco(endereco, dto);
        Endereco atualizado = enderecoRepository.save(endereco);
        return enderecoMapper.mapearParaEnderecoRespostaDTO(atualizado);
    }
}