package com.marketplace.repository;

import com.marketplace.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {
    boolean existsByProdutoIdAndPedidoId(UUID produtoId, UUID pedidoId);

    Optional<ItemPedido> findByProdutoIdAndPedidoId(UUID produtoId, UUID pedidoId);
}
