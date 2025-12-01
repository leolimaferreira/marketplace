package com.marketplace.service;

import com.marketplace.dto.login.LoginRequestDTO;
import com.marketplace.dto.login.LoginRespostaDTO;
import com.marketplace.exception.NaoAutorizadoException;
import com.marketplace.model.Usuario;
import com.marketplace.repository.UsuarioRepository;
import com.marketplace.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorizacaoService {

    private static final String EMAIL_OU_SENHA_INVALIDOS = "Email ou senha inválidos";
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional(readOnly = true)
    public LoginRespostaDTO login(LoginRequestDTO dto) {
        log.info("Login recebido com email: {}", dto.email());

        Usuario usuario = usuarioRepository.findByEmailAndAtivo(dto.email())
                .orElseThrow(() -> {
                    log.warn(EMAIL_OU_SENHA_INVALIDOS);
                    return new NaoAutorizadoException(EMAIL_OU_SENHA_INVALIDOS);
                });

        log.info("Usuário encontrado: {}, cargo: {}", usuario.getNome(), usuario.getCargo());

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            log.warn(EMAIL_OU_SENHA_INVALIDOS);
            throw new BadCredentialsException("Credenciais inválidas");
        }

        log.info("Senha validada com sucesso para usuário: {}", dto.email());

        String token = tokenService.generateToken(usuario);
        log.info("Token gerado com sucesso para usuário: {}", dto.email());

        return new LoginRespostaDTO(token, usuario.getNome());
    }
}