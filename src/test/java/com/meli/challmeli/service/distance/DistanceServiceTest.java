package com.meli.challmeli.service.distance;

import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.repository.distance.DistanceRepository;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.service.data.CoinData;
import com.meli.challmeli.service.data.DataCountryData;
import com.meli.challmeli.service.data.DistanceData;
import com.meli.challmeli.service.data.GeolocationData;
import com.meli.challmeli.service.datacountry.DataCountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.Optional;

import static com.meli.challmeli.service.data.DataStatisticData.buildDataStatistics;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
@SpringBootConfiguration
@SpringBootTest
@ContextConfiguration
@ExtendWith(SpringExtension.class)
class DistanceServiceTest {
    @Mock
    private DistanceRepository distanceRepository;

    @InjectMocks
    private DistanceService distanceService;

    @Test
    void buildDistance() {
        DataCountry dataCountry = DataCountryData.buildDataCountrySuccess();
        when(distanceRepository.findById(Integer.valueOf(dataCountry.getGeonameId()))).thenReturn(Optional.of(DistanceData.buildDistance()));
        when(distanceRepository.save(DistanceData.buildDistance())).thenReturn(DistanceData.buildDistanceResponse());
        Distance response = distanceService.buildDistance(dataCountry);
    }
}