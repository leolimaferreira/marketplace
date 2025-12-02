package com.marketplace.service;

import com.marketplace.dto.login.LoginRequestDTO;
import com.marketplace.dto.login.LoginRespostaDTO;
import com.marketplace.dto.recuperacao.RecuperacaoRequestDTO;
import com.marketplace.dto.recuperacao.RecuperacaoRespostaDTO;
import com.marketplace.dto.recuperacao.TrocarSenhaDTO;
import com.marketplace.exception.MesmaSenhaException;
import com.marketplace.exception.NaoAutorizadoException;
import com.marketplace.exception.NaoEncontradoException;
import com.marketplace.exception.TokenRecuperacaoExpiradoException;
import com.marketplace.mapper.TokenRecuperacaoSenhaMapper;
import com.marketplace.model.TokenRecuperacaoSenha;
import com.marketplace.model.Usuario;
import com.marketplace.repository.TokenRecuperacaoSenhaRepository;
import com.marketplace.repository.UsuarioRepository;
import com.marketplace.security.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.marketplace.utils.Constantes.USUARIO_NAO_ENCONTRADO;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutorizacaoService {

    private static final String EMAIL_OU_SENHA_INVALIDOS = "Email ou senha inválidos";
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final TokenRecuperacaoSenhaRepository tokenRecuperacaoSenhaRepository;
    private final TokenRecuperacaoSenhaMapper tokenRecuperacaoSenhaMapper;

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

    @Transactional
    public RecuperacaoRespostaDTO gerarTokenRecuperacao(RecuperacaoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmailAndAtivo(dto.email())
                .orElseThrow(() -> new NaoEncontradoException(USUARIO_NAO_ENCONTRADO));

        TokenRecuperacaoSenha tokenRecuperacao = new TokenRecuperacaoSenha();
        tokenRecuperacao.setUsuario(usuario);
        TokenRecuperacaoSenha tokenSalvo = tokenRecuperacaoSenhaRepository.save(tokenRecuperacao);

        return tokenRecuperacaoSenhaMapper.mapearParaRecuperacaoRespostaDTO(tokenSalvo);
    }

    @Transactional
    public void trocarSenha(TrocarSenhaDTO dto) {
        TokenRecuperacaoSenha tokenRecuperacao = tokenRecuperacaoSenhaRepository.findById(dto.tokenId())
                .orElseThrow(() -> new NaoEncontradoException("Token de recuperação não encontrado"));

        if (tokenRecuperacao.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new TokenRecuperacaoExpiradoException("Token de recuperação expirado");
        }

        Usuario usuario = usuarioRepository.findById(tokenRecuperacao.getUsuario().getId())
                .orElseThrow(() -> new NaoEncontradoException(USUARIO_NAO_ENCONTRADO));

        if (passwordEncoder.matches(dto.novaSenha(), usuario.getSenha())) {
            throw new MesmaSenhaException("Nova senha não pode ser igual a antiga");
        }

        usuario.setSenha(passwordEncoder.encode(dto.novaSenha()));
        tokenRecuperacao.setUsado(true);
        tokenRecuperacaoSenhaRepository.save(tokenRecuperacao);
        usuarioRepository.save(usuario);
    }
}