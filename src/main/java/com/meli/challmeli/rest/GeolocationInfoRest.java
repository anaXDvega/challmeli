package com.meli.challmeli.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
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
public class GeolocationInfoRest {

    @Value("${apis.geolocation.geolocationUrl}")
    String geolocationUrl;
    @Value("${apis.geolocation.geolocationToken}")
    String geolocationToken;
    private HttpClient client;
    private ObjectMapper mapper = new ObjectMapper();

    public GeolocationInfoRest() {
        this.client = HttpClient.newBuilder().connectTimeout(Duration.of(10, ChronoUnit.SECONDS)).build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public GeolocationDTO listIpInfo(String ip){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(geolocationUrl + ip +  "?access_key=" + geolocationToken)).GET().build();
        try {
            HttpResponse<String> respuesta=client.send(request,  HttpResponse.BodyHandlers.ofString());
       if (respuesta.statusCode() != 200) {
                throw new Exception("Respuesta invalida - response code " + respuesta.statusCode());
            }
            System.out.println("Dentro de geolocation>>>> "+respuesta.body());
            return new ObjectMapper().readValue(respuesta.body(), GeolocationDTO.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo info de la ip /" + ip , e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo info de la ip /" + ip , e);
        }
    }
}
