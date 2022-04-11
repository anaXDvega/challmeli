package com.meli.challmeli.service.data;
import com.meli.challmeli.model.distance.Distance;

import java.util.Optional;

public class DistanceData {

    public static Iterable<Distance> buildDistanceIterable() {
        Distance distance = new Distance();
        distance.setDistance(54.5555);
        distance.setRegionName("Cucuta");
        distance.setCountry("Colombia");
        distance.setGeonameId(54888);
        distance.setInvocations(1);
        return (Iterable<Distance>) distance;
    }

    public static Distance buildDistance() {
        return Distance.builder().invocations(1).distance(588.222222).geonameId(558).regionName("Cucuta").country("Colombia").build();
    }
    public static Distance buildDistanceResponse() {
        Distance distance = new Distance();
        distance.setDistance(588.222222);
        distance.setRegionName("Cucuta");
        distance.setCountry("Colombia");
        distance.setGeonameId(558);
        distance.setInvocations(1);
        return distance;
    }
}
