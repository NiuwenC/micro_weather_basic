package com.jackniu.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;

public class XmlBuilder {

    /**
     * 将XML字符串转换为特定的POJO类型
     * @param clazz
     * @param xmlStr
     * @return
     * @throws Exception
     */
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws  Exception{
        Object xmlObject = null;
        Reader reader = null;

        JAXBContext context = JAXBContext.newInstance(clazz);

        //将Xml转换成对象的核心接口
        Unmarshaller unmarshaller = context.createUnmarshaller();
        reader = new StringReader(xmlStr);
        xmlObject= unmarshaller.unmarshal(reader);

        if(null != reader){
            reader.close();
        }
        return xmlObject;
    }
}
