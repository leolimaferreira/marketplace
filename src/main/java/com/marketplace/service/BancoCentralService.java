package com.marketplace.service;

import com.marketplace.client.BancoCentralClient;
import com.marketplace.dto.bacen.TaxaCambioResponse;
import com.marketplace.dto.bacen.TaxaSelicResponse;
import com.marketplace.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BancoCentralService {

    private final BancoCentralClient bancoCentralClient;

    public BigDecimal obterCotacaoDolar(LocalDate data) {
        TaxaCambioResponse response = bancoCentralClient.consultarTaxaCambio("USD", data);

        if (response != null && !response.getValue().isEmpty()) {
            return response.getValue().get(0).getCotacaoVenda();
        }

        throw new NaoEncontradoException("Cotação não encontrada para a data: " + data);
    }

    public BigDecimal obterTaxaSelic() {
        TaxaSelicResponse response = bancoCentralClient.consultarTaxaSelic();

        if (response != null) {
            return response.getValor();
        }

        throw new NaoEncontradoException("Taxa SELIC não encontrada");
    }

    public BigDecimal converterParaDolar(BigDecimal valorReal, LocalDate data) {
        BigDecimal cotacao = obterCotacaoDolar(data);
        return valorReal.divide(cotacao, 2, RoundingMode.HALF_UP);
    }
}
