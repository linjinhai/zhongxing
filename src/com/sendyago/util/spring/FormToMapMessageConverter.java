/* ==============================================================
 * $ID: ServiceImpl.java, v1.0 2016/4/28 10:25:00 Rick Exp $
 * created: [2016-04-28 10:25:00] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.util.spring;

import com.sendyago.util.common.Utils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * SPRING扩展类
 * Form表单转换为Map类型
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/28 10:25:00 $
 */
public class FormToMapMessageConverter implements HttpMessageConverter<LinkedHashMap<String,String>> {
    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return LinkedHashMap.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return LinkedHashMap.class.isAssignableFrom(aClass);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> supports = new ArrayList<MediaType>();
        supports.add(MediaType.APPLICATION_FORM_URLENCODED);
        return supports;
    }

    @Override
    public LinkedHashMap<String, String> read(Class<? extends LinkedHashMap<String, String>> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String params = IOUtils.toString(httpInputMessage.getBody(), "utf-8");
        return Utils.paramsToMap(params);
    }

    @Override
    public void write(LinkedHashMap<String, String> stringStringLinkedHashMap, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        httpOutputMessage.getBody().write(stringStringLinkedHashMap.toString().getBytes());
    }

//    @Override
//    public Map<String, String> read(Class<? extends Map<String, String>> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
//        String params = IOUtils.toString(httpInputMessage.getBody(), "utf-8");
//        return Utils.paramsToMap(params);
//    }
//
//    @Override
//    public void write(Map<String, String> stringStringMap, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
//        httpOutputMessage.getBody().write(stringStringMap.toString().getBytes());
//    }
}
