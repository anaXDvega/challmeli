package com.meli.challmeli.repository.statistics;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import org.springframework.data.repository.CrudRepository;

public interface StatisticsRepository extends CrudRepository<DataStatistics, Integer> {
}
