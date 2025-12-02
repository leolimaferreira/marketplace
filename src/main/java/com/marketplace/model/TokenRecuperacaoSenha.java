package com.marketplace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens_recuperacao_senha")
@Getter
@Setter
public class TokenRecuperacaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(15);

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private boolean usado = false;
}
