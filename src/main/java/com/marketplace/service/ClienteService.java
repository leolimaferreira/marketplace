package com.marketplace.service;

import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import com.marketplace.dto.usuario.cliente.ClienteAtualizacaoDTO;
import com.marketplace.dto.usuario.cliente.ClienteCriacaoDTO;
import com.marketplace.dto.usuario.cliente.ClienteRespostaDTO;
import com.marketplace.mapper.ClienteMapper;
import com.marketplace.model.Cliente;
import com.marketplace.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;

    @Transactional
    public ClienteRespostaDTO criarCliente(ClienteCriacaoDTO dto) {
        Cliente cliente = clienteMapper.mapearParaCliente(dto);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        EnderecoCriacaoDTO endereco = dto.endereco();
        enderecoService.criarEndereco(endereco, clienteSalvo.getId());

        return clienteMapper.mapearParaClienteRespostaDTO(clienteSalvo);
    }

    public List<ClienteRespostaDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::mapearParaClienteRespostaDTO)
                .toList();
    }

    public ClienteRespostaDTO atualizarCliente(UUID clienteId, ClienteAtualizacaoDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow();

        clienteMapper.atualizarEntidade(cliente, dto);

        return clienteMapper.mapearParaClienteRespostaDTO(clienteRepository.save(cliente));
    }
}
