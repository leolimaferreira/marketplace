package com.marketplace.repository;

import com.marketplace.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {
}
