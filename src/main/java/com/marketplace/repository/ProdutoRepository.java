package com.marketplace.repository;

import com.marketplace.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    Page<Produto> findAll(Specification<Produto> specs, Pageable pageRequest);

    @Query("SELECT p FROM Produto p WHERE p.id = :id AND p.ativo = true")
    Optional<Produto> findByIdAndAtivo(UUID id);
}
