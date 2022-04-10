package com.meli.challmeli.service;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.repository.statistics.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
