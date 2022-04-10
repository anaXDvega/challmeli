package com.meli.challmeli.service;

import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.model.ErrorData.Error;
import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datacountry.ErrorDataCountry;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.repository.distance.DistanceRepository;
import com.meli.challmeli.repository.statistics.StatisticsRepository;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.rest.CoinInfoRest;
import com.meli.challmeli.rest.GeolocationInfoRest;
import com.meli.challmeli.service.datacountry.DataCountryService;
import com.meli.challmeli.service.distance.DistanceService;
import com.meli.challmeli.service.statistics.StatisticsService;
import com.meli.challmeli.util.ValidateIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class InfoIpService {
    @Autowired
    GeolocationInfoRest geolocationInfoRest;
    @Autowired
    CountryIo countryIo;

    @Autowired
    CoinInfoRest coinInfoRest;

    @Autowired
    DistanceRepository distanceRepository;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    DistanceService distanceService;

    @Autowired
    DataCountryService dataCountryService;

    public Object countryInfoComplete(String ip) {
        if (!ValidateIP.validateIPAddress(ip)) {
            throw new RuntimeException("La ip ingresada no es correcta");
        }
        return buildCountry(ip);
    }

    public Object buildCountry(String ip) {
        GeolocationDTO ipInfo = geolocationInfoRest.listIpInfo(ip);
        return ipInfo.getSuccess() != null ? buildErrorDataCountry(ipInfo.getError(), "Geolocalizacion") : validationCoin(ipInfo);
    }

    private Object validationCoin(GeolocationDTO ipInfo) {
        String countryCurrencyCode = countryIo.callOnCountryIo("currency.json", ipInfo.getCountryCode());
        var coin = coinInfoRest.buildCoin(countryCurrencyCode.equals("EUR") ? "USD" : countryCurrencyCode);
        return coin.getSuccess().equals("false") ? buildErrorDataCountry(coin.getError(), "Conversion de moneda") : buildDataCountry(ipInfo, countryCurrencyCode, coin);
    }

    public DataCountry buildDataCountry(GeolocationDTO ipInfo, String countryCurrencyCode, CoinDTO coin) {
        DataCountry dataCountry = dataCountryService.buildDataCountry(ipInfo,countryCurrencyCode, coin);
        Distance resultDistance = distanceService.buildDistance(dataCountry);
        statisticsRepository.save(findStatistics(resultDistance));
        return dataCountry;
    }

    private ErrorDataCountry buildErrorDataCountry(Error ipInfo, String module) {
        ErrorDataCountry errorDataCountry = new ErrorDataCountry();
        errorDataCountry.setSuccess("false");
        errorDataCountry.setCode(ipInfo.getCode());
        errorDataCountry.setType(ipInfo.getType());
        errorDataCountry.setInfo(ipInfo.getInfo());
        errorDataCountry.setModule(module);
        return errorDataCountry;
    }

    public DataStatistics findStatistics(Distance find) {
        Optional<DataStatistics> findStatistics = statisticsRepository.findById(1);
        return findStatistics.isEmpty() ? statisticsService.convertToItem(distanceRepository.averageDistanceToBuenosAires()) : statisticsService.updateStatistics(findStatistics.get(), find);
    }
}
