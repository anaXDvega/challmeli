package com.meli.challmeli.model.geolocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class Location {
    @JsonProperty("geoname_id")
    private String geonameId;
    @JsonProperty("capital")
    private String capital;
    @JsonProperty("languages")
    private List<Languages> languages;
    @JsonProperty("country_flag")
    private String countryFlag;
    @JsonProperty("country_flag_emoji")
    private String countryFlagEmoji;
    @JsonProperty("country_flag_emoji_unicode")
    private String countryFlagEmojiUnicode;
    @JsonProperty("calling_code")
    private String callingCode;
    @JsonProperty("is_eu")
    private Boolean isEu;
}
