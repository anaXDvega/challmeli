package com.meli.challmeli.service;
import com.meli.challmeli.model.Distance;
import com.meli.challmeli.model.ErrorData.Error;
import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datacountry.ErrorDataCountry;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.model.geolocation.Languages;
import com.meli.challmeli.model.geolocation.Location;
import com.meli.challmeli.repository.distance.DistanceRepository;
import com.meli.challmeli.rest.CodCountryRest;
import com.meli.challmeli.rest.CoinInfoRest;
import com.meli.challmeli.rest.GeolocationInfoRest;
import com.meli.challmeli.util.DistanceCalculator;
import com.meli.challmeli.util.IpValidate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoIpService {
    @Autowired
    GeolocationInfoRest geolocationInfoRest;


    @Autowired
    CodCountryRest codCountryRest;

    @Autowired
    CoinInfoRest coinInfoRest;

    @Autowired
    DistanceRepository distanceRepository;

    public Object countryInfoComplete(String ip) {
        if(!IpValidate.validateIPAddress(ip)){
            throw new RuntimeException("La ip ingresada no es correcta");
        }
        return  buildCountry(ip);
    }

    public Object buildCountry(String ip) {
      //  GeolocationDTO ipInfo = geolocationInfoRest.listIpInfo(ip);
        GeolocationDTO geolocationDTO = new GeolocationDTO();
        geolocationDTO.setIp("190.173.136.0");
        geolocationDTO.setCity("Cucutoski");
        geolocationDTO.setCountryName("Colombia");
        geolocationDTO.setCountryCode("AR");
        geolocationDTO.setRegionName("venezuela");
        geolocationDTO.setLatitude("54.55555555555");
        geolocationDTO.setLongitude("54.55555555555");
        Location location = new Location();
        Languages languages = new Languages();
        languages.setCode("es");
        languages.setName("Spanish");
        languages.setNativo("espanol");
        List<Languages> listLanguages=buildList(languages);
        location.setLanguages(listLanguages);
        location.setCallingCode("57");
        geolocationDTO.setLocation(location);

       // return ipInfo.getSuccess()!=null ? buildErrorDataCountry(ipInfo.getError(), "Geolocalizacion") :  validationCoin(ipInfo);
         return geolocationDTO.getSuccess()!=null ? buildErrorDataCountry(geolocationDTO.getError(), "Geolocalizacion") :  validationCoin(geolocationDTO);
    }
    private String getonCountryIo(String json, String countryCode){
        return codCountryRest.callOnCountryIo(json, countryCode);
    }
    private Object validationCoin(GeolocationDTO ipInfo){
        String countryCurrencyCode = getonCountryIo("currency.json", ipInfo.getCountryCode());
       // var coin = coinInfoRest.buildCoin(countryCurrencyCode.equals("EUR") ? "USD" : countryCurrencyCode);
           CoinDTO coin = new CoinDTO();
         coin.setSuccess("true");
         coin.setRates("{\"COP\":121.989028}");
      //  return coin.getSuccess().equals("false") ? buildErrorDataCountry(coin.getError(), "Conversion de moneda") : buildDataCountry(ipInfo,  countryCurrencyCode, coin);
        return buildDataCountry(ipInfo,  countryCurrencyCode, coin);
    }
    private Double convertJsonToString(Object coin, String countryCurrencyCode){
        JSONObject objetoJson = new JSONObject(coin);
        JSONObject moneda = objetoJson.getJSONObject("rates");
        return (Double) moneda.get(countryCurrencyCode);
    }
    private DataCountry buildDataCountry(GeolocationDTO ipInfo, String  countryCurrencyCode, CoinDTO coin){
        DataCountry dataCountry = new DataCountry();
        dataCountry.setIp(ipInfo.getIp());
        dataCountry.setCity(ipInfo.getCity());
        dataCountry.setCountry(ipInfo.getCountryName());
        dataCountry.setCode(ipInfo.getCountryCode());
        dataCountry.setIsoCode(getonCountryIo("iso3.json", ipInfo.getCountryCode()));
        dataCountry.setLanguages(ipInfo.getLocation().getLanguages());
        dataCountry.setCountryCurrencyCode(countryCurrencyCode);
        dataCountry.setCodeCountry(ipInfo.getLocation().getCallingCode());
       // dataCountry.setCoin(convertJsonToString(coin,countryCurrencyCode));
        dataCountry.setCoin(40000.0);
        dataCountry.setDistanceToBA(!ipInfo.getRegionName().equals("Buenos Aires") ? DistanceCalculator.distance( Double.parseDouble(ipInfo.getLatitude()), Double.parseDouble(ipInfo.getLongitude())) : 0);


        buildDistance(dataCountry);
        return dataCountry;
    }
    private ErrorDataCountry buildErrorDataCountry(Error ipInfo, String module){
        ErrorDataCountry errorDataCountry = new ErrorDataCountry();
        errorDataCountry.setCode(ipInfo.getCode());
        errorDataCountry.setType(ipInfo.getType());
        errorDataCountry.setInfo(ipInfo.getInfo());
        errorDataCountry.setModule(module);
        return errorDataCountry;
    }
    private Distance buildDistance(DataCountry dataCountry) {
        var prueba = distanceRepository.findById(Integer.valueOf(dataCountry.getCodeCountry()));
        Distance distancebuild = new Distance();
        distancebuild.setCodCountry(Integer.valueOf(dataCountry.getCodeCountry()));
        distancebuild.setDistance(Double.valueOf(dataCountry.getDistanceToBA()));
        distancebuild.setCountry(dataCountry.getCountry());
        distancebuild.setInvocations(!prueba.isEmpty() ? prueba.get().getInvocations()+1 :  1);
        return distanceRepository.save(distancebuild);
    }
    public Iterable<Distance> findAllDistance(){
        Iterable<Distance> findAllDistance = distanceRepository.findAll();
        return findAllDistance;
    }
    private List<Languages> buildList(Object... args) {
        List<Languages> languages = new ArrayList<>();
        for (Object object : args) {
            if (object instanceof Languages) {
                languages.add((Languages) object);
            }
        }
        return languages;
    }
}
