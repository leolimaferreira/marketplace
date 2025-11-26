package com.marketplace.repository;

import com.marketplace.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    @Query("SELECT c FROM Cliente c WHERE c.id = :clienteId AND c.ativo = true")
    Optional<Cliente> findByIdAndAtivo(UUID clienteId);
}
