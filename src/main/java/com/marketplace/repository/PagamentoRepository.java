package com.marketplace.repository;

import com.marketplace.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    @Query("SELECT p FROM Pagamento p JOIN p.pedido pe WHERE pe.cliente.id = :clienteId")
    List<Pagamento> findAllByClienteId(UUID clienteId);
}
