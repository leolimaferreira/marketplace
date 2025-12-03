package com.marketplace.service;

import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import com.marketplace.dto.usuario.cliente.ClienteAtualizacaoDTO;
import com.marketplace.dto.usuario.cliente.ClienteCriacaoDTO;
import com.marketplace.dto.usuario.cliente.ClienteRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.mapper.ClienteMapper;
import com.marketplace.model.Cliente;
import com.marketplace.repository.ClienteRepository;
import com.marketplace.validator.UsuarioValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.marketplace.utils.Constantes.CLIENTE_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;
    private final UsuarioValidator usuarioValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ClienteRespostaDTO criarCliente(ClienteCriacaoDTO dto) {
        Cliente cliente = clienteMapper.mapearParaCliente(dto);
        usuarioValidator.validarEmail(cliente);

        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));

        Cliente clienteSalvo = clienteRepository.save(cliente);

        EnderecoCriacaoDTO endereco = dto.endereco();
        enderecoService.criarEndereco(endereco, clienteSalvo.getId());

        return clienteMapper.mapearParaClienteRespostaDTO(clienteSalvo);
    }

    @Transactional(readOnly = true)
    public List<ClienteRespostaDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::mapearParaClienteRespostaDTO)
                .toList();
    }

    @Transactional
    public ClienteRespostaDTO encontrarClientePorId(UUID clienteId) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(clienteId)
                .orElseThrow(() -> new NaoEncontradoException(CLIENTE_NAO_ENCONTRADO));

        return clienteMapper.mapearParaClienteRespostaDTO(cliente);
    }

    @Transactional
    public ClienteRespostaDTO atualizarCliente(UUID clienteId, ClienteAtualizacaoDTO dto) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(clienteId)
                .orElseThrow(() -> new NaoEncontradoException(CLIENTE_NAO_ENCONTRADO));

        usuarioValidator.validarEmail(cliente);

        clienteMapper.atualizarCliente(cliente, dto);

        return clienteMapper.mapearParaClienteRespostaDTO(clienteRepository.save(cliente));
    }

    @Transactional
    public void deletarCliente(UUID clienteId) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(clienteId)
                .orElseThrow(() -> new NaoEncontradoException(CLIENTE_NAO_ENCONTRADO));

        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }
}
