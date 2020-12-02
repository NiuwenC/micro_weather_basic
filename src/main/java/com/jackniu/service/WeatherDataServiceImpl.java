package com.jackniu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackniu.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class WeatherDataServiceImpl implements WeatherDataService{
    @Autowired
    private RestTemplate restTemplate;

    private final String WEATHER_API="http://wthrcdn.etouch.cn/weather_mini";
    /**
     * 根据城市ID查询天气数据
     *
     * @param cityId
     * @return
     */
    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri =WEATHER_API + "?citykey="+cityId;
        return this.doGetWeatherData(uri);
    }

    /**
     * 根据城市名称查询天气数据
     *
     * @param cityName
     * @return
     */
    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri =WEATHER_API + "?city="+cityName;
        return this.doGetWeatherData(uri);
    }

    private WeatherResponse doGetWeatherData(String uri){
        ResponseEntity<String> response =  restTemplate.getForEntity(uri,String.class);
        String strBody = null;

        if(response.getStatusCodeValue() == 200){
            strBody = response.getBody();
        }

        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;
        try{
            weatherResponse = mapper.readValue(strBody,WeatherResponse.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return weatherResponse;


    }
}
