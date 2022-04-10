package com.meli.challmeli.service.distance;

import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.repository.distance.DistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {
    @Autowired
    DistanceRepository distanceRepository;


    public Iterable<Distance> findAllDistance(){
        return  distanceRepository.findAll();
    }

    public Distance buildDistance(DataCountry dataCountry) {
        var getIfExists = distanceRepository.findById(Integer.valueOf(dataCountry.getGeonameId()));
        Distance distance = new Distance();
        distance.setGeonameId(Integer.valueOf(dataCountry.getGeonameId()));
        distance.setCity(dataCountry.getCity());
        distance.setDistance(Double.valueOf(dataCountry.getDistanceToBA()));
        distance.setCountry(dataCountry.getCountry());
        distance.setInvocations(!getIfExists.isEmpty() ? getIfExists.get().getInvocations()+ 1 :  1);
        Distance resultDistance = distanceRepository.save(distance);
        return resultDistance;
    }
}
