package com.jackniu.service;

import com.jackniu.vo.Weather;
import com.jackniu.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherReportServiceImpl implements WeatherReportService {

    @Autowired
    private WeatherDataService weatherDataServiceImpl;


    /**
     * 根据城市ID查询天气信息
     *
     * @param cityId
     * @return
     */
    @Override
    public Weather getDataByCityId(String cityId) {
        WeatherResponse response = weatherDataServiceImpl.getDataByCityId(cityId);
        return response.getData();
    }
}
