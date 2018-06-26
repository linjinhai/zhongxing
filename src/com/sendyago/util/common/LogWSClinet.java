/* ==============================================================
 * $ID: LoginController.java, v1.0 2016/6/3 15:17:12 Rick Exp $
 * created: [2016-06-03 15:17:12] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.util.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * 系统主页功能控制类
 * 包括系统主页中实时曲线,均值曲线的展示,以及报警统计图形的展示,数据传输等
 * 主要用于显示各功能的曲线信息,曲线通过highcharts插件进行展示
 * 前台通过Ajax形式来访问后台数据
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0   $Date: 2016/6/3 15:17:12 $Description 类创建/logInsert()方法 &
 */
public class LogWSClinet {

    private static Log log = LogFactory.getLog(LogWSClinet.class);
    /**
     * 调用webservice接口,保存日志
     * @param param
     * @throws RemoteException
     */
    public static void logInsert(String param, String ipUrl) throws Exception {
        try {
            String[] strs = param.split(",");
            String url = ipUrl + "/log/logInsert?str1="+strs[0]+"&str2="+strs[1]+"&str3="+strs[2]+"&str4="+strs[3];
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
//                log.error(">>>>>>>>>>>>>>>调用返回值:"+lines.toString());
            }
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//          Service service = new Service();
//        Call call = (Call) service.createCall();
//        // http://zgx.com/system/ws为分派方法名,logInert为调用的方法名
//        call.setOperationName(new javax.xml.namespace.QName("http://zgx.com/system/ws","logInsert"));
//        call.setTargetEndpointAddress(url);
//        call.addParameter("param", XMLType.XSD_STRING, ParameterMode.IN);
//        // param参数值
//        call.invoke(new Object[]{param});