package com.marketplace.repository.specs;

import com.marketplace.model.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProdutoSpecs {

    private ProdutoSpecs() {
    }

    private static final String PRECO_VENDA = "precoVenda";

    public static Specification<Produto> nomeLike(String nome) {
        return (root, query, cb) -> cb.like( cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Produto> precoVendaBetween(BigDecimal precoMin, BigDecimal precoMax) {
        return (root, query, cb) -> {
            if (precoMin == null && precoMax == null) {
                return cb.conjunction();
            }

            if (precoMin != null && precoMax != null) {
                return cb.between(root.get(PRECO_VENDA), precoMin, precoMax);
            }

            if (precoMin != null) {
                return cb.greaterThanOrEqualTo(root.get(PRECO_VENDA), precoMin);
            }

            return cb.lessThanOrEqualTo(root.get(PRECO_VENDA), precoMax);
        };
    }
}
