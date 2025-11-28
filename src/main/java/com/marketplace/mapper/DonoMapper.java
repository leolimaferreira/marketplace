package com.marketplace.mapper;

import com.marketplace.dto.usuario.dono.DonoCriacaoDTO;
import com.marketplace.dto.usuario.dono.DonoRespostaDTO;
import com.marketplace.model.Dono;
import org.springframework.stereotype.Component;

@Component
public class DonoMapper {

    public Dono mapearParaDono(DonoCriacaoDTO dto) {
        Dono dono = new Dono();
        dono.setNome(dto.nome());
        dono.setEmail(dto.email());
        dono.setSenha(dto.senha());
        dono.setCpf(dto.cpf());
        dono.setRg(dto.rg());
        dono.setCelular(dto.celular());
        dono.setDataNascimento(dto.dataNascimento());
        dono.setImagem(dto.imagem());
        return dono;
    }

    public DonoRespostaDTO mapearParaDonoResposta(Dono dono) {
        return new DonoRespostaDTO(
                dono.getId(),
                dono.getNome(),
                dono.getEmail(),
                dono.getCpf(),
                dono.getRg(),
                dono.getCelular(),
                dono.getDataNascimento(),
                dono.getImagem(),
                dono.getDataCadastro(),
                dono.getDataAtualizacao(),
                dono.getAtivo()
        );
    }
}
