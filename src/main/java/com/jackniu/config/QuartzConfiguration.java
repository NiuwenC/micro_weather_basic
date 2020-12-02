package com.jackniu.config;

import com.jackniu.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    private final int TIME=1800; //更新频率

    //定义一个特定的Job，可以使用JobBuilder API轻松构建
    @Bean
    public JobDetail weatherDataSyncJobJobDetail(){
        return JobBuilder.newJob(WeatherDataSyncJob.class)
                .withIdentity("weatherDataSyncJob")
                .storeDurably()
                .build();
    }

    //定义了一个特定的Job
    @Bean
    public Trigger sampleJobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(weatherDataSyncJobJobDetail())
                .withIdentity("weatherDataSyncTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
