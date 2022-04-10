package com.meli.challmeli.service.data;

import com.meli.challmeli.model.ErrorData.ErrorData;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.model.geolocation.Languages;
import com.meli.challmeli.model.geolocation.Location;

import java.util.ArrayList;
import java.util.List;

public class GeolocationData {


    public static GeolocationDTO buildRequestGeolocation() {
        GeolocationDTO geolocationDTO = new GeolocationDTO();
        geolocationDTO.setIp("190.29.74.10");
        geolocationDTO.setType("ipv4");
        geolocationDTO.setContinentCode("SA");
        geolocationDTO.setContinentName("South America");
        geolocationDTO.setRegionCode("NAR");
        geolocationDTO.setRegionName("Nariño");
        geolocationDTO.setCity("Ureña");
        geolocationDTO.setZip("540018");
        geolocationDTO.setLatitude(7.978390216827393);
        geolocationDTO.setLongitude(-72.49826049804688);
        geolocationDTO.setLocation(buildRequestLocation());
        return geolocationDTO;
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
    public static Location buildRequestLocation(){
        Location location = new Location();
        location.setGeonameId("3625655");
        location.setCapital("Bogotá");
        location.setCountryFlag("https://assets.ipstack.com/flags/co.svg");
        location.setCountryFlagEmoji("🇨🇴");
        location.setCountryFlagEmojiUnicode("U+1F1E8 U+1F1F4");
        location.setCallingCode("57");
        Languages languages = new Languages();
        languages.setCode("es");
        languages.setName("Spanish");
        languages.setNativo("espanol");
        Languages languages2 = new Languages();
        languages2.setCode("es");
        languages2.setName("Spanish");
        languages2.setNativo("espanol");
        List<Languages> listLanguages=buildList(languages,languages2);
        location.setLanguages(listLanguages);
        location.setIsEu(false);
        return location;
    }

    public static GeolocationDTO buildRequestGeolocationFailed() {
        GeolocationDTO geolocationDTO = new GeolocationDTO();
        geolocationDTO.setSuccess("false");
        ErrorData error = new ErrorData();
        error.setCode("104");
        error.setType("usage_limit_reached");
        error.setInfo("Your Monthly usage limit has been reached. Please upgrade your subscription plan");
        geolocationDTO.setErrorData(error);
        return geolocationDTO;
    }
}
