package com.meli.challmeli.model.infocountry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class InfoCountryDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("topLevelDomain")
    private Object topLevelDomain;
    @JsonProperty("alpha2Code")
    private String alpha2Code;
    @JsonProperty("alpha3Code")
    private String alpha3Code;
    @JsonProperty("callingCodes")
    private Object callingCodes;
    @JsonProperty("capital")
    private String capital;
    @JsonProperty("altSpellings")
    private Object altSpellings;
    @JsonProperty("region")
    private String region;
}
