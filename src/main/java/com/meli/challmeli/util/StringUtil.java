package com.meli.challmeli.util;

import org.json.JSONObject;

public class StringUtil {
    public static Double convertJsonToString(Object coin, String countryCurrencyCode){
        JSONObject objetoJson = new JSONObject(coin);
        JSONObject moneda = objetoJson.getJSONObject("rates");
        return (Double) moneda.get(countryCurrencyCode);
    }
}
