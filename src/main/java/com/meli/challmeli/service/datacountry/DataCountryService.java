package com.meli.challmeli.service.datacountry;

import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
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
        return DataCountry.builder().ip(ipInfo.getIp())
                .city(ipInfo.getCity())
                .country(ipInfo.getCountryName())
                .code(ipInfo.getCountryCode())
                .isoCode(countryIo.callOnCountryIo("iso3.json", ipInfo.getCountryCode()))
                .languages(ipInfo.getLocation().getLanguages())
                .countryCurrencyCode(countryCurrencyCode)
                .codeCountry(ipInfo.getLocation().getCallingCode())
                .time(localDateTimeInUTC)
                .latitude(ipInfo.getLatitude())
                .longitude(ipInfo.getLongitude())
                .coinToConvert(countryCurrencyCode.equals("EUR") ? "USD" : "EUR")
                .geonameId(ipInfo.getLocation().getGeonameId())
                .distanceToBA(!ipInfo.getRegionName().equals("Buenos Aires") ? CalculateDistance.distance(ipInfo.getLatitude(), ipInfo.getLongitude()) : 0)
                .coin(convertJsonToString(coin, countryCurrencyCode.equals("EUR") ? "USD" : countryCurrencyCode))
                .success("true").build();
    }

}
