package com.meli.challmeli.model.ErrorData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Error {
    @JsonProperty("code")
    private String code;
    @JsonProperty("type")
    private String type;
    @JsonProperty("info")
    private String info;
}
