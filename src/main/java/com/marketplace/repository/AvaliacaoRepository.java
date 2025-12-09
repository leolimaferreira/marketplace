package com.marketplace.repository;

import com.marketplace.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {
    @Query("SELECT a FROM Avaliacao a JOIN a.pedido.itens i WHERE a.pedido.loja.id = :lojaId AND i.produto.id = :produtoId")
    List<Avaliacao> findByProdutoIdAndLojaId(UUID produtoId, UUID lojaId);
}
