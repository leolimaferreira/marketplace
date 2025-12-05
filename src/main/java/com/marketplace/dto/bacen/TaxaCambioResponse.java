package com.marketplace.dto.bacen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TaxaCambioResponse {
    @JsonProperty("value")
    private List<CotacaoMoeda> value;

    @Data
    public static class CotacaoMoeda {
        @JsonProperty("cotacaoCompra")
        private BigDecimal cotacaoCompra;

        @JsonProperty("cotacaoVenda")
        private BigDecimal cotacaoVenda;

        @JsonProperty("dataHoraCotacao")
        private String dataHoraCotacao;
    }
}
