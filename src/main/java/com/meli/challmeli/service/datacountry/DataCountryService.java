package com.meli.challmeli.service.datacountry;

import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.rest.CountryIo;
import com.meli.challmeli.util.CalculateDistance;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import static com.meli.challmeli.util.Constant.*;
import static com.meli.challmeli.util.DecimalsUtil.formatDecimals;
import static com.meli.challmeli.util.StringUtil.convertJsonToString;

@Service
public class DataCountryService {

    private final CountryIo countryIo;

    public DataCountryService(CountryIo countryIo) {
        this.countryIo = countryIo;
    }

    public DataCountry buildDataCountry(GeolocationDTO ipInfo, String countryCurrencyCode, CoinDTO coin) {
        LocalDateTime localDateTimeInUTC = LocalDateTime.now();
        return DataCountry.builder().ip(ipInfo.getIp())
                .regionName(ipInfo.getRegionName())
                .country(ipInfo.getCountryName())
                .code(ipInfo.getCountryCode())
                .isoCode(countryIo.callOnCountryIo(ISO3, ipInfo.getCountryCode()))
                .languages(ipInfo.getLocation().getLanguages())
                .countryCurrencyCode(countryCurrencyCode)
                .codeCountry(ipInfo.getLocation().getCallingCode())
                .time(localDateTimeInUTC)
                .latitude(formatDecimals(ipInfo.getLatitude(),3))
                .longitude(formatDecimals(ipInfo.getLongitude(),3))
                .coinToConvert(countryCurrencyCode.equals(EUR) ? USD : EUR)
                .geonameId(ipInfo.getLocation().getGeonameId())
                .distanceToBA(!ipInfo.getRegionName().equals(BUENOSAIRES) ? CalculateDistance.distance(ipInfo.getLatitude(), ipInfo.getLongitude()) : 0)
                .coin(convertJsonToString(coin, countryCurrencyCode.equals(EUR) ? USD : countryCurrencyCode))
                .success("true").build();
    }

}
