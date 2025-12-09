package com.marketplace.exception.handler;

import com.marketplace.dto.error.ErroCampo;
import com.marketplace.dto.error.RespostaErro;
import com.marketplace.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespostaErro handleNaoEncontradoException(NaoEncontradoException e) {
        return new RespostaErro(HttpStatus.NOT_FOUND.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(ConflitoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RespostaErro handleConflitoException(ConflitoException e) {
        return new RespostaErro(HttpStatus.CONFLICT.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleCampoInvalidoException(CampoInvalidoException e) {
        return RespostaErro.of(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                List.of(new ErroCampo(e.getCampo(), e.getMessage()))
        );
    }

    @ExceptionHandler(QuantidadeInsuficienteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleQuantidadeInsuficienteException(QuantidadeInsuficienteException e) {
        return new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(AtualizacaoStatusInvalidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleAtualizacaoStatusInvalidaException(AtualizacaoStatusInvalidaException e) {
        return new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(PedidoNaoEntregueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handlePedidoNaoEntregueException(PedidoNaoEntregueException e) {
        return new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(PedidoJaAvaliadoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handlePedidoJaAvaliadoException(PedidoJaAvaliadoException e) {
        return new RespostaErro(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public RespostaErro handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> errosList = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage())).toList();
        return new RespostaErro(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errosList);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespostaErro handleBadCredentialsException(BadCredentialsException e) {
        log.error("Autenticação falhou: {}", e.getMessage());
        return new RespostaErro(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(NaoAutorizadoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespostaErro handleNaoAutorizadoException(NaoAutorizadoException e) {
        log.error("Erro nas authorities do usuário: {}", e.getMessage());
        return new RespostaErro(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(TokenRecuperacaoExpiradoException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespostaErro handleTokenRecuperacaoExpiradoException(TokenRecuperacaoExpiradoException e) {
        log.error("Erro na validação do token: {}", e.getMessage());
        return new RespostaErro(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(MesmaSenhaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaErro handleMesmaSenhaException(MesmaSenhaException e) {
        log.error("Erro na troca de senha: {}", e.getMessage());
        return new RespostaErro(HttpStatus.BAD_REQUEST.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(InterruptedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespostaErro handleInterruptedException(InterruptedException e) {
        log.error("Erro na aprovação/rejeição do pagamento: {}", e.getMessage());
        Thread.currentThread().interrupt();
        return new RespostaErro(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), List.of());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespostaErro handleException(Exception e) {
        log.error("Erro interno do servidor: {}", e.getMessage());
        return new RespostaErro(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor", List.of());
    }
}
