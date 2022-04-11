package com.meli.challmeli.rest;
import com.meli.challmeli.service.statistics.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ContextConfiguration(classes = CoinInfoRestTest.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoinInfoRestTest {
    @Mock
    private StatisticsService statisticsService;
    @Mock
    private HttpClient client;
    @InjectMocks
    private CoinInfoRest coinInfoRest;
    private static final String URL = "http://data.fixer.io/api/";
    private static final String TOKEN = "e46ea8bd7df0b60acb1da7709e32260a";


    @Test
    void countryInfoCompleteSuccess() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL +  "latest?access_key=" + TOKEN + "&symbols=" + "COP"))
                .timeout(Duration.ofMillis(5009))
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
    }
}