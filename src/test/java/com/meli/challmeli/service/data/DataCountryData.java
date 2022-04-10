package com.meli.challmeli.service.data;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.geolocation.Languages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataCountryData {
    public static DataCountry buildDataCountrySuccess() {
        LocalDateTime localDateTimeInUTC = LocalDateTime.now();
        Languages languages = new Languages();
        languages.setCode("es");
        languages.setName("Spanish");
        languages.setNativo("espanol");
        Languages languages2 = new Languages();
        languages2.setCode("es");
        languages2.setName("Spanish");
        languages2.setNativo("espanol");
        List<Languages> listLanguages=buildList(languages,languages2);
        return DataCountry.builder().ip("190.29.74.10")
                .city("Ureña")
                .country("Colombia")
                .code("57")
                .isoCode("COP")
                .languages(listLanguages)
                .countryCurrencyCode("COP")
                .codeCountry("57")
                .time(localDateTimeInUTC)
                .latitude(54.2222222)
                .longitude(54.55555555)
                .coinToConvert("EUR")
                .geonameId("5568")
                .distanceToBA(588.222222)
                .coin(4.000)
                .success("true").build();
    }

    private static List<Languages> buildList(Object... args) {
        List<Languages> languages = new ArrayList<>();
        for (Object object : args) {
            if (object instanceof Languages) {
                languages.add((Languages) object);
            }
        }
        return languages;
    }
}
