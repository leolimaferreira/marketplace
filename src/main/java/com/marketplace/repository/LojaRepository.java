package com.marketplace.repository;

import com.marketplace.model.Loja;
import com.marketplace.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LojaRepository extends JpaRepository<Loja, UUID> {
    @Query("SELECT l FROM Loja l JOIN l.produtos p WHERE p = :produto")
    List<Loja> findLojasByProduto(Produto produto);

    @Query("SELECT l FROM Loja l WHERE l.id = :lojaId AND l.ativo = true")
    Optional<Loja> findByIdAndAtivo(UUID lojaId);

    @Query("SELECT l FROM Loja l WHERE l.ativo = true")
    List<Loja> findAllAtivo();
}
