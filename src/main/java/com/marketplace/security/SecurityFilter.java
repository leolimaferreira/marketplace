package com.marketplace.security;

import com.auth0.jwt.JWT;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.model.Usuario;
import com.marketplace.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static com.marketplace.utils.Constantes.USUARIO_NAO_ENCONTRADO;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        log.debug("Token recuperado: {}", token != null ? "TOKEN_EXISTS" : "NO_TOKEN");

        if (token != null) {
            var login = tokenService.validateToken(token);
            log.debug("Login do token: {}", login);

            if (login != null) {
                Usuario usuario = usuarioRepository.findByIdAndAtivo(UUID.fromString(login))
                        .orElseThrow(() -> new NaoEncontradoException(USUARIO_NAO_ENCONTRADO + login));

                String role = JWT.decode(token).getClaim("role").asString();

                log.debug("Usuário do Banco de dados - Email: {}, Cargo: {}", usuario.getEmail(), usuario.getCargo());
                log.debug("Cargo do token: {}", role);

                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                log.debug("Authorities criadas: {}", authorities);

                var authentication = new UsernamePasswordAuthenticationToken(usuario.getId().toString(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("Authentication set with ID as principal: {}", usuario.getId());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}