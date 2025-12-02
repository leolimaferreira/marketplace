package com.marketplace.mapper;

import com.marketplace.dto.recuperacao.RecuperacaoRespostaDTO;
import com.marketplace.model.TokenRecuperacaoSenha;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRecuperacaoSenhaMapper {

    private final UsuarioMapper usuarioMapper;

    public RecuperacaoRespostaDTO mapearParaRecuperacaoRespostaDTO(TokenRecuperacaoSenha token) {
        return new RecuperacaoRespostaDTO(
                token.getId(),
                token.getDataExpiracao(),
                usuarioMapper.mapearParaUsuarioRespostaDTO(token.getUsuario())
        );
    }
}
