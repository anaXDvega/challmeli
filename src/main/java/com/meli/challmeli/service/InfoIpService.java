package com.meli.challmeli.service;
import com.meli.challmeli.model.Distance;
import com.meli.challmeli.model.ErrorData.Error;
import com.meli.challmeli.model.coin.CoinDTO;
import com.meli.challmeli.model.datacountry.DataCountry;
import com.meli.challmeli.model.datacountry.ErrorDataCountry;
import com.meli.challmeli.model.datastatistics.DataStatistics;
import com.meli.challmeli.model.geolocation.GeolocationDTO;
import com.meli.challmeli.model.geolocation.Languages;
import com.meli.challmeli.model.geolocation.Location;
import com.meli.challmeli.repository.distance.DistanceRepository;
import com.meli.challmeli.repository.statistics.StatisticsRepository;
import com.meli.challmeli.rest.CodCountryRest;
import com.meli.challmeli.rest.CoinInfoRest;
import com.meli.challmeli.rest.GeolocationInfoRest;
import com.meli.challmeli.util.DistanceCalculator;
import com.meli.challmeli.util.IpValidate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    StatisticsRepository statisticsRepository;

    public Object countryInfoComplete(String ip) {
        if(!IpValidate.validateIPAddress(ip)){
            throw new RuntimeException("La ip ingresada no es correcta");
        }
        System.out.println("dentto");
        return buildCountry(ip);
    }

    public Object buildCountry(String ip) {
   // GeolocationDTO ipInfo = geolocationInfoRest.listIpInfo(ip);
        GeolocationDTO geolocationDTO = new GeolocationDTO();
        geolocationDTO.setIp("190.173.136.0");
        geolocationDTO.setCity("Cucutoski");
        geolocationDTO.setCountryName("Colombia");
        geolocationDTO.setCountryCode("DE");
        geolocationDTO.setRegionName("venezuela");
        geolocationDTO.setLatitude(54.55555555555);
        geolocationDTO.setLongitude(54.55555555555);
        Location location = new Location();
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
        location.setCallingCode("57");
        location.setGeonameId("65555555");
        geolocationDTO.setLocation(location);

        //  return ipInfo.getSuccess()!=null ? buildErrorDataCountry(ipInfo.getError(), "Geolocalizacion") :  validationCoin(ipInfo);
       return geolocationDTO.getSuccess()!=null ? buildErrorDataCountry(geolocationDTO.getError(), "Geolocalizacion") :  validationCoin(geolocationDTO);
    }
    private String getonCountryIo(String json, String countryCode){
        return codCountryRest.callOnCountryIo(json, countryCode);
    }
    private Object validationCoin(GeolocationDTO ipInfo){
        String countryCurrencyCode = getonCountryIo("currency.json", ipInfo.getCountryCode());
    //    var coin = coinInfoRest.buildCoin(countryCurrencyCode.equals("EUR") ? "USD" : countryCurrencyCode);
        CoinDTO coin = new CoinDTO();
        coin.setSuccess("true");
        coin.setRates("{\"COP\":121.989028}");
      //   return coin.getSuccess().equals("false") ? buildErrorDataCountry(coin.getError(), "Conversion de moneda") : buildDataCountry(ipInfo,  countryCurrencyCode, coin);
         return buildDataCountry(ipInfo,  countryCurrencyCode, coin);
    }
    private Double convertJsonToString(Object coin, String countryCurrencyCode){
        JSONObject objetoJson = new JSONObject(coin);
        JSONObject moneda = objetoJson.getJSONObject("rates");
        return (Double) moneda.get(countryCurrencyCode);
    }
    private DataCountry buildDataCountry(GeolocationDTO ipInfo, String  countryCurrencyCode, CoinDTO coin){
        LocalDateTime localDateTimeInUTC = LocalDateTime.now();
        DataCountry dataCountry = new DataCountry();
        dataCountry.setIp(ipInfo.getIp());
        dataCountry.setCity(ipInfo.getCity());
        dataCountry.setCountry(ipInfo.getCountryName());
        dataCountry.setCode(ipInfo.getCountryCode());
        dataCountry.setIsoCode(getonCountryIo("iso3.json", ipInfo.getCountryCode()));
        dataCountry.setLanguages(ipInfo.getLocation().getLanguages());
        dataCountry.setCountryCurrencyCode(countryCurrencyCode);
        dataCountry.setCodeCountry(ipInfo.getLocation().getCallingCode());
        dataCountry.setTime(localDateTimeInUTC);
        dataCountry.setLatitude(ipInfo.getLatitude());
        dataCountry.setLongitude(ipInfo.getLongitude());
        dataCountry.setGeonameId(ipInfo.getLocation().getGeonameId());
        dataCountry.setCoinToConvert(countryCurrencyCode.equals("EUR") ? "USD" : "EUR");
       // dataCountry.setCoin(convertJsonToString(coin,countryCurrencyCode.equals("EUR") ? "USD" : countryCurrencyCode));
        dataCountry.setSuccess("true");
        dataCountry.setCoin(40000.0);
        dataCountry.setDistanceToBA(!ipInfo.getRegionName().equals("Buenos Aires") ? DistanceCalculator.distance(ipInfo.getLatitude(), ipInfo.getLongitude()) : 0);


        buildDistance(dataCountry);
        return dataCountry;
    }
    private ErrorDataCountry buildErrorDataCountry(Error ipInfo, String module){
        ErrorDataCountry errorDataCountry = new ErrorDataCountry();
        errorDataCountry.setSuccess("false");
        errorDataCountry.setCode(ipInfo.getCode());
        errorDataCountry.setType(ipInfo.getType());
        errorDataCountry.setInfo(ipInfo.getInfo());
        errorDataCountry.setModule(module);
        return errorDataCountry;
    }
    private Distance buildDistance(DataCountry dataCountry) {
        var getIfExists = distanceRepository.findById(Integer.valueOf(dataCountry.getGeonameId()));
        Distance distancebuild = new Distance();
        distancebuild.setGeonameId(Integer.valueOf(dataCountry.getGeonameId()));
        distancebuild.setCity(dataCountry.getCity());
        distancebuild.setDistance(Double.valueOf(dataCountry.getDistanceToBA()));
        distancebuild.setCountry(dataCountry.getCountry());
        distancebuild.setInvocations(!getIfExists.isEmpty() ? getIfExists.get().getInvocations()+ 1 :  1);
        Distance distance = distanceRepository.save(distancebuild);
        findStadisctis(distance);
        return distance;
    }
    private DataStatistics convertToItem(Map<String, ?> item) {
        DataStatistics dataStatistics = new DataStatistics();
        dataStatistics.setId(1);
        dataStatistics.setAverage((Double) item.get("average"));
        dataStatistics.setCantInvocations(((BigInteger)  item.get("cantInvocations")).intValue());
        dataStatistics.setMax((Double) item.get("max"));
        dataStatistics.setMin((Double) item.get("min"));
        return statisticsRepository.save(dataStatistics);
    }

    public Iterable<Distance> findAllDistance(){
        Iterable<Distance> find = distanceRepository.findAll();
        return find;
    }

    private DataStatistics updateStatistics(DataStatistics dataStatistics, Distance distance) {
        System.out.println("dentro de update");
        var sumDistances = dataStatistics.getAverage() * dataStatistics.getCantInvocations() + distance.getDistance();
        var cant = dataStatistics.getCantInvocations() + 1;
        var average = sumDistances / cant;
        DataStatistics dataStatisticsUpdate = new DataStatistics();
        dataStatisticsUpdate.setId(1);
        dataStatisticsUpdate.setCantInvocations(cant);
        dataStatisticsUpdate.setMax(dataStatistics.getMax() > distance.getDistance() ? dataStatistics.getMax() : distance.getDistance());
        dataStatisticsUpdate.setMin(dataStatistics.getMin() < distance.getDistance() ? dataStatistics.getMin() : distance.getDistance());
        dataStatisticsUpdate.setAverage(average);
        return statisticsRepository.save(dataStatisticsUpdate);

    }

    public Object findStadisctis(Distance find){
        System.out.println("dentro de findStadisctis");
        Optional<DataStatistics> findStadisctis = statisticsRepository.findById(1);
        System.out.println(findStadisctis);
         if (findStadisctis.isEmpty()){
           return convertToItem(distanceRepository.averageDistanceToBuenosAires());
        }else{
         return updateStatistics(findStadisctis.get(), find);
        }
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
