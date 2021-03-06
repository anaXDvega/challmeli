package com.meli.challmeli.service;

import com.meli.challmeli.model.ErrorData.ErrorData;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datacountry.ErrorDataCountry;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.rest.CoinInfoRest;
import com.meli.challmeli.rest.GeolocationInfoRest;
import com.meli.challmeli.service.datacountry.DataCountryService;
import com.meli.challmeli.service.distance.DistanceService;
import com.meli.challmeli.service.statistics.StatisticsService;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static com.meli.challmeli.util.Constant.*;

@Service
public class InfoIpService {
    private final GeolocationInfoRest geolocationInfoRest;

    private final CountryIo countryIo;

    private final CoinInfoRest coinInfoRest;

    private final StatisticsService statisticsService;

    private final DistanceService distanceService;

    private final DataCountryService dataCountryService;

    public InfoIpService(GeolocationInfoRest geolocationInfoRest, CountryIo countryIo, CoinInfoRest coinInfoRest, StatisticsService statisticsService, DistanceService distanceService, DataCountryService dataCountryService) {
        this.geolocationInfoRest = geolocationInfoRest;
        this.countryIo = countryIo;
        this.coinInfoRest = coinInfoRest;
        this.statisticsService = statisticsService;
        this.distanceService = distanceService;
        this.dataCountryService = dataCountryService;
    }

    public Object countryInfoComplete(String ip) {
        GeolocationDTO ipInfo = geolocationInfoRest.listIpInfo(ip);
        return ipInfo.getSuccess() != null ? buildErrorDataCountry(ipInfo.getErrorData(), GEOLOCALIZACION) : validationCoin(ipInfo);
    }

    private Object validationCoin(GeolocationDTO ipInfo) {
        String countryCurrencyCode = countryIo.callOnCountryIo(CURRENCY, ipInfo.getCountryCode());
        var coin = coinInfoRest.buildCoin(countryCurrencyCode.equals(EUR) ? USD : countryCurrencyCode);
        return coin.getSuccess().equals("false") ? buildErrorDataCountry(coin.getErrorData(), CONVERSIONMONEDAMODULO) : buildDataCountry(ipInfo, countryCurrencyCode, coin);
    }

    public DataCountry buildDataCountry(GeolocationDTO ipInfo, String countryCurrencyCode, CoinDTO coin) {
        DataCountry dataCountry = dataCountryService.buildDataCountry(ipInfo,countryCurrencyCode, coin);
        Distance resultDistance = distanceService.buildDistance(dataCountry);
        statisticsService.save(findStatistics(resultDistance));
        return dataCountry;
    }

    private ErrorDataCountry buildErrorDataCountry(ErrorData ipInfo, String module) {
        return new ErrorDataCountry("false",ipInfo.getCode(),ipInfo.getType(),ipInfo.getInfo(),module);
    }

    public DataStatistics findStatistics(Distance find) {
        Optional<DataStatistics> findStatistics = statisticsService.findById(1);
        return findStatistics.isEmpty() ? statisticsService.convertToItem(distanceService.averageDistanceToBuenosAires()) : statisticsService.updateStatistics(findStatistics.get(), find);
    }
}
