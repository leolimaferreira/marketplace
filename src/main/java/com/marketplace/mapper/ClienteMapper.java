package com.marketplace.mapper;

import com.marketplace.dto.endereco.EnderecoRespostaDTO;
import com.marketplace.dto.usuario.ClienteCriacaoDTO;
import com.marketplace.dto.usuario.ClienteRespostaDTO;
import com.marketplace.model.Cliente;
import com.marketplace.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final EnderecoMapper enderecoMapper;

    public Cliente mapearParaCliente(ClienteCriacaoDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setSenha(dto.senha());
        cliente.setAtivo(true);
        cliente.setCelular(dto.celular());
        cliente.setCpf(dto.cpf());
        cliente.setRg(dto.rg());
        cliente.setDataNascimento(dto.dataNascimento());
        cliente.setImagem(dto.imagem());
        cliente.setEnderecos(new ArrayList<>());
        return cliente;
    }

    public ClienteRespostaDTO mapearParaClienteRespostaDTO(Cliente cliente) {
        List<Endereco> enderecos = cliente.getEnderecos();

        List<EnderecoRespostaDTO> enderecosRespostaDTO = enderecos
                .stream()
                .map(enderecoMapper::mapearParaEnderecoRespostaDTO)
                .toList();

        return new ClienteRespostaDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getRg(),
                cliente.getCelular(),
                cliente.getDataNascimento(),
                cliente.getImagem(),
                enderecosRespostaDTO,
                cliente.getDataCadastro(),
                cliente.getDataAtualizacao()
        );
    }
}
