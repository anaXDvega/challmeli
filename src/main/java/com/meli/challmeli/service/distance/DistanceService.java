package com.meli.challmeli.service.distance;

import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.repository.distance.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DistanceService {
    @Autowired
    DistanceRepository distanceRepository;


    public Iterable<Distance> findAllDistance(){
        return  distanceRepository.findAll();
    }

    public Map<String, ?> averageDistanceToBuenosAires(){return distanceRepository.averageDistanceToBuenosAires();}


    public Distance buildDistance(DataCountry dataCountry) {
        var getIfExists = distanceRepository.findById(Integer.valueOf(dataCountry.getGeonameId()));
         Distance distance = Distance.builder().geonameId(Integer.valueOf(dataCountry.getGeonameId()))
                .country(dataCountry.getCountry())
                .city(dataCountry.getCity())
                .distance(Double.valueOf(dataCountry.getDistanceToBA()))
                .invocations(!getIfExists.isEmpty() ? getIfExists.get().getInvocations()+ 1 :  1).build();
        Distance resultDistance = distanceRepository.save(distance);
        return resultDistance;
    }
}
