package com.jackniu.service;

import com.jackniu.util.XmlBuilder;
import com.jackniu.vo.City;

import com.jackniu.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CityDataServiceImpl  implements CityDataService{

    @Override
    public List<City> listCity() throws Exception {
        //读取定义的xml文件
        Resource resource= new ClassPathResource("a.xml");
        System.out.println(resource.exists());
        BufferedReader br = new BufferedReader(new InputStreamReader(
                resource.getInputStream(),"utf-8"
        ));
        StringBuffer buffer = new StringBuffer();
        String line = "";

        while((line = br.readLine())!=null){
            buffer.append(line);
        }

        //将XML转换成Java对象
        CityList cityList = (CityList) XmlBuilder.xmlStrToObject(CityList.class,
                buffer.toString());

        System.out.println("citylist数据内容"+ cityList);

        return cityList.getCityList();
    }
}
