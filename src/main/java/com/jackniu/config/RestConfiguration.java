package com.jackniu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//Configration 注解：声明当前类是一个配置类，相当于 Spring 中的一个 XML 文件
@Configuration
public class RestConfiguration {


    @Autowired
    private RestTemplateBuilder builder;

    //Bean 注解：作用在方法上，声明当前方法的返回值是一个 Bean
    // 然后在一个Controller或者Service中获取并使用这个Bean，具体就是Autowired注解注入
    @Bean
    public RestTemplate restTemplate(){
        return builder.build();
    }
}
