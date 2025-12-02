package com.marketplace.mapper;

import com.marketplace.dto.usuario.UsuarioRespostaDTO;
import com.marketplace.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioRespostaDTO mapearParaUsuarioRespostaDTO(Usuario usuario) {
        return new UsuarioRespostaDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCargo()
        );
    }
}
