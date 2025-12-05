package com.marketplace.dto.bacen;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxaSelicResponse {
    @JsonProperty("data")
    private String data;

    @JsonProperty("valor")
    private BigDecimal valor;
}