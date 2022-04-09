package com.meli.challmeli.service;
import com.meli.challmeli.model.Distance;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.repository.distance.DistanceRepository;
import com.meli.challmeli.rest.CodCountryRest;
import com.meli.challmeli.rest.GeolocationInfoRest;
import com.meli.challmeli.util.IpValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class InfoIpService {
    @Autowired
    GeolocationInfoRest geolocationInfoRest;
    @Autowired
    CodCountryRest codCountryRest;
    @Autowired
    DistanceRepository distanceRepository;

    public String countryInfoComplete(String ip) {
        if(!IpValidate.validateIPAddress(ip)){
            throw new RuntimeException("La ip ingresada no es correcta");
        }
        buildCountry(ip);
        return ip;
    }
    public DataCountry buildCountry(String ip) {
        var ipInfo = geolocationInfoRest.listIpInfo(ip);
        var codIso = codCountryRest.callOnCountryIo("iso3.json", ipInfo.getCountryCode());
        DataCountry dataCountry = new DataCountry();
        dataCountry.setIp(ip);
        dataCountry.setCountry(ipInfo.getCountryName());
        dataCountry.setCode(ipInfo.getCountryCode());
        dataCountry.setIsoCode(codIso);
        dataCountry.setLanguages(ipInfo.getLocation().getLanguages());
        dataCountry.setCountryCurrencyCode("57");
        dataCountry.setDistanceToBA(Double.parseDouble(ipInfo.getLongitude()));
        distanceRepository.findById(57);
        buildDistance();
        return dataCountry;
    }
    private Optional<Distance> buildDistance() {
        Distance distancebuild = new Distance();
        distancebuild.setCodCountry(57);
        distancebuild.setDistance(1.0);
        distancebuild.setCountry("Colombia");
        distancebuild.setInvocations(1);
        distanceRepository.save(distancebuild);
        return distanceRepository.findById(57);
    }
}
