package com.marketplace.dto.loja;

import com.marketplace.dto.produto.ProdutoRespostaDTO;
import com.marketplace.dto.usuario.dono.DonoRespostaDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LojaRespostaDTO(
        UUID id,
        String nome,
        String descricao,
        String imagem,
        String cnpj,
        DonoRespostaDTO dono,
        List<ProdutoRespostaDTO> produtos,
        LocalDateTime criadaEm,
        LocalDateTime atualizadaEm,
        Boolean ativo
) {
}
