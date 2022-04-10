package com.meli.challmeli.service.datacountry;

import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.distance.Distance;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.util.CalculateDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import static com.meli.challmeli.util.StringUtil.convertJsonToString;

@Service
public class DataCountryService {
    @Autowired
    CountryIo countryIo;
    public DataCountry buildDataCountry(GeolocationDTO ipInfo, String countryCurrencyCode, CoinDTO coin) {
        LocalDateTime localDateTimeInUTC = LocalDateTime.now();
        DataCountry dataCountry = new DataCountry();
        dataCountry.setIp(ipInfo.getIp());
        dataCountry.setCity(ipInfo.getCity());
        dataCountry.setCountry(ipInfo.getCountryName());
        dataCountry.setCode(ipInfo.getCountryCode());
        dataCountry.setIsoCode(countryIo.callOnCountryIo("iso3.json", ipInfo.getCountryCode()));
        dataCountry.setLanguages(ipInfo.getLocation().getLanguages());
        dataCountry.setCountryCurrencyCode(countryCurrencyCode);
        dataCountry.setCodeCountry(ipInfo.getLocation().getCallingCode());
        dataCountry.setTime(localDateTimeInUTC);
        dataCountry.setLatitude(ipInfo.getLatitude());
        dataCountry.setLongitude(ipInfo.getLongitude());
        dataCountry.setGeonameId(ipInfo.getLocation().getGeonameId());
        dataCountry.setCoinToConvert(countryCurrencyCode.equals("EUR") ? "USD" : "EUR");
        dataCountry.setCoin(convertJsonToString(coin, countryCurrencyCode.equals("EUR") ? "USD" : countryCurrencyCode));
        dataCountry.setSuccess("true");
        dataCountry.setDistanceToBA(!ipInfo.getRegionName().equals("Buenos Aires") ? CalculateDistance.distance(ipInfo.getLatitude(), ipInfo.getLongitude()) : 0);
        return dataCountry;
    }

}
