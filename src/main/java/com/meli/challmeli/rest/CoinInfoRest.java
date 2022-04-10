package com.meli.challmeli.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.meli.challmeli.model.coin.CoinDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class CoinInfoRest {
    @Value("${apis.coinInfo.coinInfoUrl}")
    String coinInfoUrl;
    @Value("${apis.coinInfo.coinInfoToken}")
    String coinInfoToken;
    HttpClient client;
    ObjectMapper mapper = new ObjectMapper();

    public CoinInfoRest() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }


    public CoinDTO buildCoin(String coin){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(coinInfoUrl +  "latest?access_key=" + coinInfoToken + "&symbols=" + coin)).GET().build();
        try {
            HttpResponse<String> respuesta=client.send(request,  HttpResponse.BodyHandlers.ofString());
            if (respuesta.statusCode() != 200) {
                throw new Exception("Respuesta invalida - response code " + respuesta.statusCode());
            }
            return new ObjectMapper().readValue(respuesta.body(), CoinDTO.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo info del tipo de moneda /" + coin , e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo info del tipo de moneda /" + coin , e);
        }
    }

}
