package com.meli.challmeli.service.statistics;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.repository.statistics.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    public DataStatistics save(DataStatistics dataStatistics){
        return statisticsRepository.save(dataStatistics);
    }

    public Iterable<DataStatistics> findAllDistance(){
        return  statisticsRepository.findAll();
    }

    public DataStatistics convertToItem(Map<String, ?> item) {
        DataStatistics dataStatistics = new DataStatistics();
        dataStatistics.setId(1);
        dataStatistics.setAverage((Double) item.get("average"));
        dataStatistics.setCantInvocations(((BigInteger)  item.get("cantInvocations")).intValue());
        dataStatistics.setMax((Double) item.get("max"));
        dataStatistics.setMin((Double) item.get("min"));
        return dataStatistics;
    }

    public DataStatistics updateStatistics(DataStatistics dataStatistics, Distance distance) {
        var sumDistances = dataStatistics.getAverage() * dataStatistics.getCantInvocations() + distance.getDistance();
        var cant = dataStatistics.getCantInvocations() + 1;
        var average = sumDistances / cant;
        DataStatistics dataStatisticsUpdate = new DataStatistics();
        dataStatisticsUpdate.setId(1);
        dataStatisticsUpdate.setCantInvocations(cant);
        dataStatisticsUpdate.setMax(dataStatistics.getMax() > distance.getDistance() ? dataStatistics.getMax() : distance.getDistance());
        dataStatisticsUpdate.setMin(dataStatistics.getMin() < distance.getDistance() ? dataStatistics.getMin() : distance.getDistance());
        dataStatisticsUpdate.setAverage(average);
        return dataStatisticsUpdate;
    }
}
