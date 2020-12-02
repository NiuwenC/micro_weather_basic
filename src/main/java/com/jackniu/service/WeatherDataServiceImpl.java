package com.jackniu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackniu.vo.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Service
public class WeatherDataServiceImpl implements WeatherDataService{
    private final static Logger logger = LoggerFactory.getLogger(WeatherDataService.class);
    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private StringRedisTemplate stringRedisTemplate; //更加专注于基于字符串的操作，数据的格式是JSON字符串
    private final Long TIME_OUT=1800L;





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

    @Override
    public void syncDataByCityId(String cityId) {
        String uri = WEATHER_API + "?citykey=" + cityId;
        this.saveWeatherData(uri);
    }

    public void saveWeatherData(String uri){
        ValueOperations<String,String> ops = this.stringRedisTemplate.opsForValue();
        String key = null;
        String strBody = null;
        ResponseEntity<String> response =  restTemplate.getForEntity(uri,String.class);
        if(response.getStatusCodeValue() == 200){
            strBody = response.getBody();
        }
        ops.set(key,strBody,TIME_OUT, TimeUnit.SECONDS);

    }


    private WeatherResponse doGetWeatherData(String uri){
        ValueOperations<String,String> ops = this.stringRedisTemplate.opsForValue();
        String key =uri;
        String strBody = null;
        // 先检查缓存，如果没有再查服务
        if (!this.stringRedisTemplate.hasKey(key)){
            logger.info("未找到key "+key);
            ResponseEntity<String> response =  restTemplate.getForEntity(uri,String.class);
            if(response.getStatusCodeValue() == 200){
                strBody = response.getBody();
            }
            ops.set(key,strBody,TIME_OUT, TimeUnit.SECONDS);
        }else{
            logger.info("找到key "+key + ", value= "+ ops.get(key));
            strBody = ops.get(key);
        }
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;
        try{
            weatherResponse = mapper.readValue(strBody,WeatherResponse.class);
        }catch (IOException e){
            logger.error("JSON反序列化失败！" ,e);
        }
        return weatherResponse;


    }
}
