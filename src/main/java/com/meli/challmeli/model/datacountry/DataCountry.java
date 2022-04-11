package com.meli.challmeli.model.datacountry;

import com.meli.challmeli.model.geolocation.Languages;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


@lombok.Data
@Builder
public class DataCountry{
    private String ip;
    private String regionName;
    private String country;
    private String code;
    private String isoCode;
    private List<Languages> languages;
    private String countryCurrencyCode;
    private String codeCountry;
    private LocalDateTime time;
    private Double latitude;
    private Double longitude;
    private String coinToConvert;
    private String geonameId;
    private Double distanceToBA;
    private Double coin;
    private String success;
}
