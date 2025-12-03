package com.marketplace.repository;

import com.marketplace.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId AND e.principal = true")
    Optional<Endereco> findPrincipalByClienteId(UUID clienteId);

    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId")
    List<Endereco> findAllByClienteId(UUID clienteId);
}
