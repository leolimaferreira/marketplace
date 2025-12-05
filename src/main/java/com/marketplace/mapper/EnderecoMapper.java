package com.marketplace.mapper;

import com.marketplace.dto.endereco.EnderecoAtualizacaoDTO;
import com.marketplace.dto.endereco.EnderecoCriacaoDTO;
import com.marketplace.dto.endereco.EnderecoRespostaDTO;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.Cliente;
import com.marketplace.model.Endereco;
import com.marketplace.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EnderecoMapper {

    private final ClienteRepository clienteRepository;

    public Endereco mapearParaEndereco(EnderecoCriacaoDTO dto, UUID clienteId) {
        Cliente cliente = clienteRepository.findByIdAndAtivo(clienteId)
                .orElseThrow(() -> new NaoEncontradoException("Cliente nao encontrado"));

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setCep(dto.cep());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCliente(cliente);
        endereco.setPrincipal(dto.principal() != null && dto.principal());
        return endereco;
    }

    public EnderecoRespostaDTO mapearParaEnderecoRespostaDTO(Endereco endereco) {
        return new EnderecoRespostaDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getPrincipal()
        );
    }

    public void atualizarEndereco(Endereco endereco, EnderecoAtualizacaoDTO dto) {
        if (dto.logradouro() != null) endereco.setLogradouro(dto.logradouro());
        if (dto.numero() != null) endereco.setNumero(dto.numero());
        if (dto.complemento() != null) endereco.setComplemento(dto.complemento());
        if (dto.cep() != null) endereco.setCep(dto.cep());
        if (dto.bairro() != null) endereco.setBairro(dto.bairro());
        if (dto.cidade() != null) endereco.setCidade(dto.cidade());
        if (dto.estado() != null) endereco.setEstado(dto.estado());
        if (dto.principal() != null) endereco.setPrincipal(dto.principal());
    }
}
