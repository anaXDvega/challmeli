package com.meli.challmeli.service.statistics;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.repository.statistics.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Service
public class StatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    public DataStatistics save(DataStatistics dataStatistics){
        return statisticsRepository.save(dataStatistics);
    }
    public Optional<DataStatistics> findById(Integer id){
        return statisticsRepository.findById(id);
    }
    public Iterable<DataStatistics> findAllDistance(){
        return  statisticsRepository.findAll();
    }

    public DataStatistics convertToItem(Map<String, ?> item) {
        return new DataStatistics(1,(Double) item.get("average"),(Double) item.get("min"),(Double) item.get("max"),((BigInteger)  item.get("cantInvocations")).intValue());
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
