package com.meli.challmeli.model.datacountry;

import com.meli.challmeli.model.geolocation.Languages;

import java.util.List;


@lombok.Data
public class DataCountry{
    private String ip;
    private String city;
    private String country;
    private String code;
    private String isoCode;
    private List<Languages> languages;
    private String countryCurrencyCode;
    private String codeCountry;
//    private LocalDateTime currentTimeInUTC;
//    private List<LocalDateTime> currentTimes;
    private Double distanceToBA;
    private Double coin;
}
