package com.meli.challmeli.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.mikael.urlbuilder.UrlBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class CountryIo {
    @Value("${apis.countryIo.countryIoUrl}")
    String countryInfoUrl;
    HttpClient client;
    ObjectMapper mapper = new ObjectMapper();

    public CountryIo() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public String callOnCountryIo(String path,String codCurrency){
        URI uri = UrlBuilder.empty().fromString(countryInfoUrl + path).toUri();
        try {
            var request = HttpRequest.newBuilder(uri).timeout(Duration.of(10, ChronoUnit.SECONDS)).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new Exception("Respuesta invalida - response code " + response.statusCode());
            }
            JSONObject objetoJson = new JSONObject(response.body());
            return objetoJson.getString(codCurrency);
        } catch (Exception e) {
            throw new RuntimeException("country.io obteniendo la informacion de todos los paises /", e);
        }
    }
}
