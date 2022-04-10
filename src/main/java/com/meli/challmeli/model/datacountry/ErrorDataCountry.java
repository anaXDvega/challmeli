package com.meli.challmeli.model.datacountry;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class ErrorDataCountry {
    private String success;
    private String code;
    private String type;
    private String info;
    private String module;
}
