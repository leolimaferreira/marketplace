package com.marketplace.validator;

import com.marketplace.exception.ConflictoException;
import com.marketplace.model.Usuario;
import com.marketplace.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validarEmail(Usuario usuario) {
        if (existeUsuarioComMesmoEmail(usuario)) {
            throw new ConflictoException("Já existe um usuário com esse email cadastrado");
        }
    }

    private boolean existeUsuarioComMesmoEmail(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmailAndAtivo(usuario.getEmail());

        if (usuario.getId() == null) {
            return usuarioEncontrado.isPresent();
        }
        return usuarioEncontrado.isPresent() && !usuario.getId().equals(usuarioEncontrado.get().getId());
    }
}
