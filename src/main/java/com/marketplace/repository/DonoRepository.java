package com.marketplace.repository;

import com.marketplace.model.Dono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DonoRepository extends JpaRepository<Dono, UUID> {
    @Query("SELECT d FROM Dono d WHERE d.email = :dono AND d.ativo = true")
    Optional<Dono> findByEmailAndAtivo(String dono);
}
