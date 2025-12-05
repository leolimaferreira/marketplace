package com.marketplace.client;

import com.marketplace.dto.bacen.TaxaCambioResponse;
import com.marketplace.dto.bacen.TaxaSelicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class BancoCentralClient {

    private static final String BASE_URL = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata";
    private final RestTemplate restTemplate;

    public TaxaCambioResponse consultarTaxaCambio(String moeda, LocalDate data) {
        String dataFormatada = data.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String url = String.format("%s/CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)?@moeda='%s'&@dataCotacao='%s'&$format=json",
                BASE_URL, moeda, dataFormatada);

        return restTemplate.getForObject(url, TaxaCambioResponse.class);
    }

    public TaxaSelicResponse consultarTaxaSelic() {
        String url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados/ultimos/1?formato=json";

        TaxaSelicResponse[] response = restTemplate.getForObject(url, TaxaSelicResponse[].class);
        return response != null && response.length > 0 ? response[0] : null;
    }
}
