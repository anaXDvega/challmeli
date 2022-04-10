package com.meli.challmeli.service.data;

import com.meli.challmeli.model.ErrorData.ErrorData;
import com.meli.challmeli.model.coin.CoinDTO;

public class CoinData {
    public static CoinDTO buildCoin() {
        CoinDTO coinDTO = new CoinDTO();
        coinDTO.setSuccess("true");
        coinDTO.setDate("2022-04-10");
        coinDTO.setBase("EUR");
        coinDTO.setTimestamp("1649624524");
        coinDTO.setRates("{\"COP\": 4111.021454}");
        return coinDTO;
    }

    public static CoinDTO buildCoinError() {
        CoinDTO coinDTO = new CoinDTO();
        coinDTO.setSuccess("false");
        ErrorData error = new ErrorData();
        error.setCode("104");
        error.setType("usage_limit_reached");
        error.setInfo("Your Monthly usage limit has been reached. Please upgrade your subscription plan");
        coinDTO.setErrorData(error);
        return coinDTO;
    }
}
