package com.meli.challmeli.service.datacountry;

import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.service.data.CoinData;
import com.meli.challmeli.service.data.GeolocationData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootConfiguration
@SpringBootTest
@ContextConfiguration
@ExtendWith(SpringExtension.class)
class DataCountryServiceTest {
    @Mock
    private CountryIo countryIo;
    @InjectMocks
    private DataCountryService dataCountryService;
    @Test
    void buildDataCountrySuccess() {
        when(countryIo.callOnCountryIo("iso3.json", "57")).thenReturn("COL");
        DataCountry response = dataCountryService.buildDataCountry(GeolocationData.buildRequestGeolocation(),"COP", CoinData.buildCoinForDataCountry());
    }
}