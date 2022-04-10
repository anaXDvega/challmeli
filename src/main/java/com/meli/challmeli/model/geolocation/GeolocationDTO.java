package com.meli.challmeli.model.geolocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meli.challmeli.model.ErrorData.ErrorData;
import lombok.Data;


@Data
public class GeolocationDTO {
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("type")
    private String type;
    @JsonProperty("continent_code")
    private String continentCode;
    @JsonProperty("continent_name")
    private String continentName;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("region_code")
    private String regionCode;
    @JsonProperty("region_name")
    private String regionName;
    @JsonProperty("city")
    private String city;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("success")
    private String success;
    @JsonProperty("errorData")
    private ErrorData errorData;
}
