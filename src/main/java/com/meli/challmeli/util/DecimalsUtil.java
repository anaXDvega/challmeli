package com.meli.challmeli.util;

public class DecimalsUtil {
    public static Double formatDecimals(Double number, Integer numberDecimals) {
        return Math.round(number * Math.pow(10, numberDecimals)) / Math.pow(10, numberDecimals);
    }
}
