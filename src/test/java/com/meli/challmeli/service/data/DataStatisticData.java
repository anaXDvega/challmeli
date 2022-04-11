package com.meli.challmeli.service.data;
import com.meli.challmeli.model.datastatistics.DataStatistics;

public class DataStatisticData {




    public static DataStatistics buildDataStatistics() {
        DataStatistics dataStatistics = new DataStatistics();
        dataStatistics.setId(1);
        dataStatistics.setAverage(2.2222);
        dataStatistics.setCantInvocations(4);
        dataStatistics.setMax(15.5555500);
        dataStatistics.setMin(15.55);
        return dataStatistics;
    }
}
