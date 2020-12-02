package com.jackniu.job;

import com.jackniu.service.CityDataService;
import com.jackniu.service.WeatherDataService;
import com.jackniu.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class WeatherDataSyncJob extends QuartzJobBean {
    private final static Logger logger = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Autowired
    private CityDataService cityDataServiceImpl;

    @Autowired
    private WeatherDataService weatherDataServiceImpl;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Start天气数据同步任务");

        List<City> cityList = null;

        try{
            cityList= cityDataServiceImpl.listCity();
        }catch (Exception e){
            logger.error("获取城市信息异常！" ,e);
        }

        for(City city:cityList){
            String cityId = city.getCityId();
            logger.info("天气数据同步任务中");

            weatherDataServiceImpl.syncDataByCityId(cityId);
        }
        logger.info("End 天气数据同步任务");

    }
}
