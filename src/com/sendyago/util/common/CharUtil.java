/* ==============================================================
 * $ID: SystemController.java, v1.1 2016/4/27 14:13:00 Rick Exp $
 * created: [2016-04-25 13:28:22] by Rick
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

/**
 * 字符串工具处理类
 * 用于处理字符串/整数/及其他数据的工具类
 *
 * @author $Author: Rick$
 * @version $Revision: 1.1 $Date: 2016/4/27 14:13:00 $
 */
public class CharUtil {

    /**
     * 将NULL转为空字符串
     * @param str 一个字符串
     * @return  如果输入的字符串为NULL则返回空
     */
    public static String null2Str(String str){
        if(null == str || "null".equals(str)){
            return "";
        }
        return str;
    }

    /**
     * 将NULL转为空字符串
     * @param obj 一个任意对象
     * @return  如果输入的字符串为NULL则返回空
     */
    public static String null2Str(Object obj){
        if(null == obj || "null".equals(obj)){
            return "";
        }
        return obj.toString();
    }

    /**
     * 将NULL转为整型数
     * @param obj 一个任意对象
     * @return  返回0或其本身
     */
    public static Integer null2Int(Object obj) {
        if(null == obj || "null".equals(obj)){
            return 0;
        }
        return (Integer) obj;
    }

}
