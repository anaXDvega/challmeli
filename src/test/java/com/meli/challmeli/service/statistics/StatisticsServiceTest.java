package com.meli.challmeli.service.statistics;

import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.repository.statistics.StatisticsRepository;
import com.meli.challmeli.service.InfoIpService;
import com.meli.challmeli.service.data.CoinData;
import com.meli.challmeli.service.data.DataCountryData;
import com.meli.challmeli.service.data.DataStatisticData;
import com.meli.challmeli.service.data.GeolocationData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.meli.challmeli.service.data.DataStatisticData.buildDataStatistics;
import static com.meli.challmeli.service.data.DistanceData.buildDistance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootConfiguration
@SpringBootTest
@ContextConfiguration
@ExtendWith(SpringExtension.class)
class StatisticsServiceTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void saveDataStatiticsSuccess() {
        when(statisticsRepository.save(any())).thenReturn(buildDataStatistics());
        DataStatistics response = statisticsService.save(buildDataStatistics());
        assertEquals(response.getAverage(), 2.2222);
    }

    @Test
    void findByIdStatiticsSuccess() {
        when(statisticsRepository.findById(any())).thenReturn(Optional.of(buildDataStatistics()));
        Optional<DataStatistics> response = statisticsService.findById(1);
        assertEquals(response.get().getMin(), 15.55);
    }
    @Test
    void updateStatistics() {
       DataStatistics response = statisticsService.updateStatistics(buildDataStatistics(), buildDistance());
       var sumDistances = buildDataStatistics().getAverage() * buildDataStatistics().getCantInvocations() + buildDistance().getDistance();
       var cant = buildDataStatistics().getCantInvocations() + 1;
       var average = sumDistances / cant;
       assertEquals(response.getCantInvocations(), cant);
       assertEquals(response.getAverage(), average);
       assertEquals(response.getMax(), buildDistance().getDistance());
       assertEquals(response.getMin(), buildDataStatistics().getMin());
    }
}