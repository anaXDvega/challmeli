package com.meli.challmeli.service;

import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datacountry.ErrorDataCountry;
import com.meli.challmeli.rest.CoinInfoRest;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.rest.GeolocationInfoRest;
import com.meli.challmeli.service.data.CoinData;
import com.meli.challmeli.service.data.DataCountryData;
import com.meli.challmeli.service.data.GeolocationData;
import com.meli.challmeli.service.datacountry.DataCountryService;
import com.meli.challmeli.service.distance.DistanceService;
import com.meli.challmeli.service.statistics.StatisticsService;
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
class InfoIpServiceTest {
    @Mock
    private StatisticsService statisticsService;

    @Mock
    private DistanceService distanceService;

    @Mock
    private DataCountryService dataCountryService;
    @Mock
    private CountryIo countryIo;
    @Mock
    private GeolocationInfoRest geolocationInfoRest;
    @Mock
    private CoinInfoRest coinInfoRest;

    @InjectMocks
    private InfoIpService infoIpService;

    @Test
    void countryInfoCompleteSuccess() {
        String ip="190.29.74.10";
        when(geolocationInfoRest.listIpInfo(ip)).thenReturn(GeolocationData.buildRequestGeolocation());
        when(countryIo.callOnCountryIo(any(),any())).thenReturn("COP");
        when(coinInfoRest.buildCoin(any())).thenReturn(CoinData.buildCoin());
        when(dataCountryService.buildDataCountry(GeolocationData.buildRequestGeolocation(),"COP", CoinData.buildCoin())).thenReturn(DataCountryData.buildDataCountrySuccess());
        Object response = infoIpService.countryInfoComplete(ip);
        assertNotNull(response);
        assertTrue(response instanceof DataCountry);
        DataCountry dataCountry = (DataCountry) response;
        assertEquals(dataCountry.getIp(), ip);
        assertEquals(dataCountry.getSuccess(), "true");
    }
    @Test
    void countryInfoCompleteFailedInGeolocationData() {
        String ip="190.29.74.10";
        when(geolocationInfoRest.listIpInfo(ip)).thenReturn(GeolocationData.buildRequestGeolocationFailed());
        Object response = infoIpService.countryInfoComplete(ip);
        assertNotNull(response);
        assertTrue(response instanceof ErrorDataCountry);
        ErrorDataCountry dataCountry = (ErrorDataCountry) response;
        assertEquals(dataCountry.getCode(), "104");
    }
    @Test
    void countryInfoCompleteFailedInValidationCoin() {
        String ip="190.29.74.10";
        when(geolocationInfoRest.listIpInfo(ip)).thenReturn(GeolocationData.buildRequestGeolocation());
        when(countryIo.callOnCountryIo(any(),any())).thenReturn("COP");
        when(coinInfoRest.buildCoin(any())).thenReturn(CoinData.buildCoinError());
        when(dataCountryService.buildDataCountry(GeolocationData.buildRequestGeolocation(),"COP", CoinData.buildCoin())).thenReturn(DataCountryData.buildDataCountrySuccess());
        Object response = infoIpService.countryInfoComplete(ip);
        assertNotNull(response);
        assertTrue(response instanceof ErrorDataCountry);
        ErrorDataCountry dataCountry = (ErrorDataCountry) response;
        assertEquals(dataCountry.getCode(), "104");
    }
}