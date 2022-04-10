package com.meli.challmeli.controller;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StadisticsController {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/generateStadistics")
    public Iterable<DataStatistics>  generateStadistics() {
        return statisticsService.findAllDistance();
    }
}
