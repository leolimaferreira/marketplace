package com.marketplace.security;

import com.marketplace.model.Usuario;
import com.marketplace.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marketplace.utils.Constantes.USUARIO_NAO_ENCONTRADO;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailAndAtivo(username).orElseThrow(() -> new UsernameNotFoundException(USUARIO_NAO_ENCONTRADO));

        return new User(usuario.getEmail(), usuario.getSenha(), List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getCargo())));
    }
}
