package com.meli.challmeli.model.coin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meli.challmeli.model.ErrorData.Error;
import lombok.Data;

@Data
public class CoinDTO {
    @JsonProperty("success")
    private String success;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("base")
    private String base;
    @JsonProperty("date")
    private String date;
    @JsonProperty("rates")
    private Object rates;
    @JsonProperty("error")
    private Error error;
}
