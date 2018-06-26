/* ==============================================================
 * $ID: MainController.java, v1.4.3 2016/6/8 15:40:12 Rick Exp $
 * created: [2016-05-18 13:47:12] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
 * ==============================================================
 */
package com.sendyago.main.controller;

import com.sendyago.main.service.MainService;
import com.sendyago.system.service.LogService;
import com.sendyago.system.service.LoginService;
import com.sendyago.util.common.LogWSClinet;
import com.sendyago.util.common.Utils;
import com.sendyago.util.context.CONFIG;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 系统主页功能控制类
 * 包括系统主页中实时曲线,均值曲线的展示,以及报警统计图形的展示,数据传输等
 * 主要用于显示各功能的曲线信息,曲线通过highcharts插件进行展示
 * 前台通过Ajax形式来访问后台数据
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0   $Date: 2016/5/18 13:47:12 $Description 类创建 &
 * @version $Revision: 1.1   $Date: 2016/5/18 16:34:12 $Description 创建getMonitorLines()方法,实时曲线图 &
 * @version $Revision: 1.2   $Date: 2016/5/19 10:10:12 $Description 创建getHourLines()方法,时均值曲线图 &
 * @version $Revision: 1.3   $Date: 2016/5/23 14:56:12 $Description 创建getWarnList()方法,最新的报警列表&
 * @version $Revision: 1.4   $Date: 2016/5/24 10:46:12 $Description 创建getWarnLine()方法,查询报警曲线 &
 * @version $Revision: 1.4.1 $Date: 2016/6/2 11:20:12 $Description 创建getMonitorPoint()方法,查询报警曲线 &
 * @version $Revision: 1.4.2 $Date: 2016/6/3 15:18:12 $Description 创建logInsert()方法,日志保存 &
 * @version $Revision: 1.4.3 $Date: 2016/6/8 15:40:12 $Description 创建getHourPoint()方法,每小时获取时均值 &
 */
@Controller
@RequestMapping("/mcontroller")
@Scope("prototype")
public class MainController {

    @Autowired
    @Qualifier("mainServiceImpl")
    private MainService service;

    @Autowired
    @Qualifier("loginServiceImpl")
    private LoginService loginService;

    @Autowired
    @Qualifier("logService")
    private LogService logService;

    /**
     * 实时查询所有传感器的数据/状态等
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "getAllSensorDatas")
    public void getAllSensorDatas( HttpServletResponse rep,
                                  @RequestParam LinkedHashMap<String, Object> params) {
        try {
            List list = service.queryAllSensorUpdate(params);
            JSONArray jsonArray = JSONArray.fromObject(list);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取实时曲线的数据
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "monitorLines")
    public void getMonitorLines( HttpServletResponse rep,
                                @RequestParam LinkedHashMap<String, Object> params) {
        try {
            Map map = service.queryMonitorLines(params);
            JSONArray jsonArray = JSONArray.fromObject(map);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 根据存储时间间隔获取数据
     * 每次获取数据需要判断与上次数据的时间是否相等,相等则不显示
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "getPoint")
    public void getMonitorPoint( HttpServletResponse rep,
                                @RequestParam LinkedHashMap<String, Object> params) {
        try {
            Map map = service.queryMonitorPoint(params);
            // 将日期转为毫秒
            String date = (String) map.get("UPDATE_TIME");
            long millis = Utils.getMillis2(date);
            map.put("UPDATE_TIME", millis);
            // 将结果发送到前台
            JSONArray jsonArray = JSONArray.fromObject(map);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每小时获取时均值
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "getHourPoint")
    public void getHourPoint( HttpServletResponse rep,
                               @RequestParam LinkedHashMap<String, Object> params) {
        try {
            Map map = service.queryHourPoint(params);
            JSONArray jsonArray = JSONArray.fromObject(map);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取时均值曲线数据
     * 根据传感器编号查询过去24小时每小时时均值
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "hourLines")
    public void getHourLines(HttpServletResponse rep,
                             @RequestParam LinkedHashMap<String, Object> params) {
        try {
            Map map = service.queryHourLines(params);
            JSONArray jsonArray = JSONArray.fromObject(map);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能: 报警柱状图统计信息查询
     * 七天的报警次数统计
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "warnColumn")
    public void getWarnColumn( HttpServletResponse rep,
                              @RequestParam LinkedHashMap<String, Object> params) {
        try {
            params.put("IN_LEVEL", 1);
            List level1List = service.queryWarnColumnS(params);
            params.put("IN_LEVEL", 2);
            List level2List = service.queryWarnColumnS(params);
            params.put("IN_LEVEL", 3);
            List level3List = service.queryWarnColumnS(params);

            List levelList = new ArrayList();
            levelList.add(level1List);
            levelList.add(level2List);
            levelList.add(level3List);

            JSONArray jsonArray = JSONArray.fromObject(levelList);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能:展示首页最新报警信息列表
     * @param req 请求的作用域
     * @param params 传入的参数信息
     * @return
     */
    @RequestMapping(value = "warnList")
    public String getWarnList(HttpServletRequest req,
                            @RequestParam LinkedHashMap<String, Object> params) {
        try {
            // 清除前台传递的无效参数
            params.remove("shiro_menu_id");
            params.remove("shiro_role_id");
            params.remove("shiro_user_id");
            // 查询最新报警列表
            List list = getWarnListPublic(params);
            req.setAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "main/warnList";
    }

    /**
     * 加载更多报警信息列表
     * @param rep  响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "warnListAjax")
    public void getWarnListByAjax( HttpServletResponse rep,
                            @RequestParam LinkedHashMap<String, Object> params) {
        try {
            // 查询最新报警列表
            List list = getWarnListPublic(params);
            JSONArray jsonArray = JSONArray.fromObject(list);
            rep.getWriter().print(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 查询报警曲线
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     * @return
     */
    @RequestMapping(value = "warnLine")
    public void getWarnLine( HttpServletResponse rep,
                              @RequestParam LinkedHashMap<String, Object> params) {
        try {
            Map map = service.queryWarnLineList(params);
            JSONArray jsonArray = JSONArray.fromObject(map);
            rep.getWriter().print(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询最新报警信息列表 -- 公共方法
     * 同时将查询内容的状态设为已查看状态
     * @param params 传入的参数信息
     * @return
     */
    private List getWarnListPublic(LinkedHashMap<String, Object> params) {
        // 查询最新报警列表
        List list = null;
        try {
            list = service.queryWarnList(params);
            // 更新已查询出报警信息的状态为已查看状态
            for(int i=0; i<list.size(); i++) {
                Map map = (Map)list.get(i);
                String warning_id = map.get("WARNING_ID").toString();
                params = new LinkedHashMap<String,Object>();
                params.put("warning_id", warning_id);
                // 更新已查询出的报警信息的最新状态
                service.updateWarnStatus(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 功能: 更新报警信息的状态为已读
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "updateWarnStatus")
    public void updateWarnStatus( HttpServletResponse rep,
                                 @RequestParam LinkedHashMap<String, Object> params) {
        try {
            service.updateWarnStatus(params);
            rep.getWriter().print(CONFIG.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存日志信息
     * @param req  请求的作用域
     * @param rep  响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "menuLogInsert")
    public void menuLogInsert(HttpServletRequest req,HttpServletResponse rep,
                          @RequestParam LinkedHashMap<String, Object> params) {
        try {
            String menuId = (String)params.get("menu_id"); // 菜单id
            String uuid = UUID.randomUUID().toString(); // uuid
            String userId = (String)((Map)req.getSession().getAttribute(CONFIG.LOGINED)).get("USER_ID"); // 用户id

            // 保存菜单查看日志
            LinkedHashMap<String, Object> logMap = new LinkedHashMap<String,Object>();
            logMap.put("str1", uuid);
            logMap.put("str2", userId);
            logMap.put("str3", menuId);
            logMap.put("str4", 9);
            logMap.put("ip", null);
            logService.logInsert(logMap);

            rep.getWriter().print(CONFIG.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 查询报警数量
     * @param rep 响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "checkWarnNum")
    public void checkWarnNum( HttpServletResponse rep,
                             @RequestParam LinkedHashMap<String, Object> params) {
        params.put("page", "");
        params.put("limit", "");
        params.put("IS_COUNT", 1);
        try {
            List list = service.queryWarnList(params);
            if(list.size() > 0) {
                Map map = (Map)list.get(0);
                int count = Integer.valueOf(map.get("NUM").toString());
                rep.getWriter().print(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 删除今天以前的随机数据
     * @param rep 响应的作用域
     */
    @RequestMapping(value = "deleteRandomData")
    public void deleteRandomData( HttpServletResponse rep) {
        try {
            service.deleteRandomData();
            rep.getWriter().print("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能: 设置传感器的随机值,开启或关闭随机数功能
     * @param rep  响应的作用域
     * @param params 传入的参数信息
     */
    @RequestMapping(value = "startOrCloseRandom")
    public void startOrCloseRandom( HttpServletResponse rep,
                                 @RequestParam LinkedHashMap<String, Object> params) {
        try {
            service.setRandomStatus(params);
            rep.getWriter().print(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能: 进入设置随机数页面
     * @param req 请求的作用域
     */
    @RequestMapping(value = "setRandom")
    public String setRandom(HttpServletRequest req) {
        List list = null;
        try {
            list = service.getSensorList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("sensors", list);
        return "main/randomDataSet";
    }
}
