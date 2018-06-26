/* ==============================================================
 * $ID: PublicController.java, v1.0 2016/4/28 13:32:32 Rick Exp $
 * created: [2016-04-28 13:32:32] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.system.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 所有信息管理公共控制层类
 * 用于管理所有页面信息的页面跳转等操作
 * 其中,toSomewhere()方法为所有页面的跳转公共方法
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/28 13:32:32$
 */
@Controller
@RequestMapping("/publicCtrl")
@Scope("prototype")
public class PublicController {

    /**
     * 跳转到功能页面的公共方法,如果从前台传递一个参数,可通过"键-值"对的方式进行传递
     * 即先获取"paramName"的值,将"paramName"的值作为参数名重新获取真正要传的值.
     * 如果要传递多个参数,那么就需要定义多个"paramName"
     * @param modelMap 传参的集合
     * @param req 请求
     * @return
     */
    @RequestMapping(value = "toSomeWhere")
    public String toSomeWhere(ModelMap modelMap, HttpServletRequest req) {
        String returnValue = "";
        try {
            // 获取前台传参的名称
            String str1 = req.getParameter("str1");
            String str2 = req.getParameter("str2");
            if(str1 == null) {
                str1 = "str1";
            }
            if(str2 == null) {
                str2 = "str2";
            }
            // 根据传参的名称获取其对应的值
            String val1 = req.getParameter(str1);
            String val2 = req.getParameter(str2);
            // 获取跳转页的名称
            returnValue = req.getParameter("returnValue");
            // 重新将要传的参数封装再传回到前台
            modelMap.put(str1,val1);
            modelMap.put(str2,val2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
