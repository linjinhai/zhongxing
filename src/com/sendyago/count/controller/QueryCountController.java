/* ==============================================================
* $ID: QueryCountController.java, v1.0 2016/5/4 17:13:01 zq Exp $
* created: [2016-5-4 17:13:101] by zq
* ==============================================================
* 健康监测系统模块统计分析查询
* ==============================================================
* Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
* ==============================================================
*/
package com.sendyago.count.controller;


import com.sendyago.count.service.QueryCountService;
import com.sendyago.util.common.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//import oracle.net.aso.p;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述类 ： 数据查询与统计
 * 用于页面 查询 ：数据查询、对比查询、统计分析查询、预警数据查询
 * 描述类声明方法
 * @author  $Author: zq  
 * @version $Revision:1.0 2016年5月5日 下午4:54:08 $
 *
 */
@Controller
@RequestMapping("/queryCount")
@Scope("prototype")
public class QueryCountController {
    @Autowired
    @Qualifier("queryCountServiceImpl")
    private QueryCountService service;

    /**
     * @Description:历史数据查询及曲线图 
     * @param  rep
     * @param  request
     * @param  params
     * @return String   
     * @throws
     */
    @RequestMapping(value = "queryList")
    public void queryList(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) {
        try {
                // 当flag=1时查询分页数据
                params.put("flag", 1);
                if(CharUtil.null2Str(params.get("page")).equals("")){
                params.put("page", 1);}          
                List<LinkedHashMap<String, Object>> page_list = service.queryForList(params);
                // 当flag=0时查询分页数据
                params.put("flag", 0);
                // 用于曲线图将列名大写转换小写
                List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                List<LinkedHashMap<String, Object>> listLow = service.queryForList(params);
                list = UpToLow(params, listLow);          

                // 查询传感器
               Map<Object, String> sensor = service.queryForObject(params);
                if (params.get("divFlag").toString().equals("div1")) {
                  SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(), params.get("menu_id").toString(), "4"); 
             
               
                }else {
                   SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(),params.get("menu_id").toString(), "5");   
                  
                }

                // 组装json数据
                JSONObject s = new JSONObject();
                s.put("sensor", sensor.get("PART_NAME").toString());
                s.put("divFlag", CharUtil.null2Str(params.get("divFlag")));
                s.put("page_list", page_list);
                s.put("list", list);
                s.put("map", params);
                rep.getWriter().print(s.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
    * 
    * @Description: 数据查询信息对比2个项目显示列表数据和曲线图
    * @param  rep
    * @param  request
    * @param  params
    * @return String    返回类型
    * @throws
    */

    @RequestMapping(value = "queryListCompare")
    public void queryList0(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) {
        try {
            long start = System.currentTimeMillis();
                // 当flag=1时查询分页数据
                // 查询分页数据项目1
                params.put("flag", 1);
                List<LinkedHashMap<String, Object>> page_list = service.queryForList(params);
                // 查询分页数据项目2
                List<LinkedHashMap<String, Object>> page_list0 = service.queryForList0(params);

                request.setAttribute("map", params);
                // 当flag=0时查询分页数据
                // 查询所有记录的条数项目2
                params.put("flag", 0);
                List<LinkedHashMap<String, Object>> listLow = service.queryForList(params);
                // 用于曲线图将列名大写转换小写
        
                List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                list = UpToLow(params, listLow);
               
                // 查询所有记录的条数项目2
                params.put("flag", 0);
                List<LinkedHashMap<String, Object>> listLow0 = service.queryForList0(params);
                // 获取list中map字段名，大写变小写
                List<HashMap<String, Object>> list0 = new ArrayList<HashMap<String, Object>>();
                list0 = UpToLow(params, listLow0);
                // 查询传感器
                params.put("s_sensor", params.get("s_sensor"));
                Map<Object, String> sensor = service.queryForObject(params);
                params.put("s_sensor", params.get("s_sensor0"));
                Map<Object, String> sensor0 = service.queryForObject(params);
                if (params.get("divFlag").toString().equals("div1")) {
                    SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(), params.get("menu_id").toString(), "4");  
                }else {
                    SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(), params.get("menu_id").toString(), "5");   
                }
                // 组装json数据
                JSONObject s = new JSONObject();
                s.put("sensor", sensor.get("PART_NAME").toString());
                s.put("sensor0", sensor0.get("PART_NAME").toString());
                s.put("divFlag", CharUtil.null2Str(params.get("divFlag")));
                s.put("page_list", page_list);
                s.put("list", list);
                s.put("page_list0", page_list0);
                s.put("list0", list0);
                s.put("map", params);
                long end = System.currentTimeMillis();
                System.out.println(""+(end-start));
                 
                rep.getWriter().print(s.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @Description:数据统计分析信息
     * @param  rep
     * @param  request
     * @param  params
     * @return String    返回类型
     * @throws
     */
    @RequestMapping(value = "queryListCount")
    public void queryListCount(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) {

      
            try {
                // 统计类型
                String tx = "";
                if (CharUtil.null2Str(params.get("countType")).equals("1")) {
                    tx = "avg";
                } else if (CharUtil.null2Str(params.get("countType")).equals("2")) {
                    tx = "max";
                } else if (CharUtil.null2Str(params.get("countType")).equals("3")) {
                    tx = "min";
                }
                List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                // 组装json数据
                JSONObject s = new JSONObject();
                // 日期
                if (CharUtil.null2Str(params.get("sdw")).equals("1")) {
                    params.put("b_date", params.get("sdw2"));
                    params.put("e_date", params.get("sdw3"));
                    params.put("tx", tx);
                    List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                    list = UpToLow(params, listLow);
                    s.put("list", list);
                }
                // 周
                else if (CharUtil.null2Str(params.get("sdw")).equals("2")) {
                    params.put("a_date", params.get("sdw4"));
                    params.put("b_date", params.get("sdw5"));
                    params.put("e_date", params.get("sdw6"));
                    params.put("tx", tx);
                    for (int i = Integer.parseInt(params.get("sdw5").toString()); i <= Integer
                            .parseInt(params.get("sdw6").toString()); i++) {
                        params.put("para", i);
                        List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);                       
                        for (LinkedHashMap<String, Object> linkedHashMap : listLow) {
                            Iterator<String> iter = linkedHashMap.keySet().iterator();
                            HashMap<String, Object> map = new HashMap<>();
                            while (iter.hasNext()) {
                                String key = iter.next().toString();
                                String key0 = key.toLowerCase();
                                map.put(key0, linkedHashMap.get(key));
                            }
                            list.add(map);
                        }

                    }
                    s.put("list", list);
                }
                // 月
                if (CharUtil.null2Str(params.get("sdw")).equals("3")) {
                    params.put("b_date", params.get("sdw7"));
                    params.put("e_date", params.get("sdw8"));
                    params.put("tx", tx);
                    List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                    list = UpToLow(params, listLow);
                    s.put("list", list);
                }
                // 年
                else if (CharUtil.null2Str(params.get("sdw")).equals("4")) {
                    params.put("b_date", params.get("sdw9"));
                    params.put("e_date", params.get("sdw10"));
                    params.put("tx", tx);
                    List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                    list = UpToLow(params, listLow);
                    s.put("list", list);
                }
                // 查询传感器
                Map<Object, String> sensor = service.queryForObject(params);
                if ( CharUtil.null2Str(params.get("divFlag")).equals("div1")) {
                    SystemCountService.OperationLog(Utils.getUUID(),   params.get("user_id").toString(), params.get("menu_id").toString(), "4");  
                }else {
                    SystemCountService.OperationLog(Utils.getUUID(),   params.get("user_id").toString(), params.get("menu_id").toString(), "5");   
                }
                s.put("sensor", sensor.get("PART_NAME").toString());
                s.put("divFlag", CharUtil.null2Str(params.get("divFlag")));
                s.put("map", params);
                rep.getWriter().print(s.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        

    }

    /**
     * @Description:预警数据查询
     * @param  rep
     * @param  request
     * @param  params
     * @return String
     * @throws
     */ 
    @RequestMapping(value = "queryListWarn")
    public String queryListWarn(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params,
            @RequestParam(value = "currentPage", defaultValue = "1") String currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") String pageSize,
             String viewFlag) {
        try {
            
            String   menu_id= CharUtil.null2Str(params.get("shiro_menu_id"));
            String   user_id= CharUtil.null2Str(params.get("shiro_user_id"));        
            String   role_id= CharUtil.null2Str(params.get("shiro_role_id")); 
            params.put("currentPage", currentPage);
            params.put("pageSize", pageSize); 
            params.put("flag", 1);
            params.put("viewFlag", viewFlag);
            PageBean pageBean = service.createQueryPageForWarn(params);         
         
           SystemCountService.OperationLog(Utils.getUUID(),user_id, menu_id, "4");
          
            request.setAttribute("shiro_menu_id", menu_id);
            request.setAttribute("shiro_user_id", user_id);
            request.setAttribute("shiro_role_id", role_id);
            request.setAttribute("map", params);
            request.setAttribute("pageBean", pageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "warn/queryWarnData";

    }

    /**
     * @Description:展示报警曲线图
     * @param  rep
     * @param  request
     * @param  params
     * @return void   
     * @throws
     */
    @RequestMapping(value = "queryListLineWarn")
    public void queryListLineWarn(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) {
        try {
            params.put("s_sensor", params.get("s_sensor"));
            Map map = service.queryLineWarn(params);
         
            JSONArray json = JSONArray.fromObject(map);
            rep.getWriter().print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @Description: 历史数据查询,对比数据查询、预警查询、统计查询导出 
     * @param  rep 输出响应信息
     * @param  request 请求数据
     * @param  params
     * @method exportExcel  
     * @return String
     * @throws
     */
    @RequestMapping(value = "exportExcel")
    public String exportExcel(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) throws Exception {
        
        try {
            //params.get("user_id").toString()
            String outFlag = params.get("viewFlag").toString(); 
             
            String tx = "";
            List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
            if (CharUtil.null2Str(params.get("countType")).equals("1")) {
                tx = "avg";
            } else if (CharUtil.null2Str(params.get("countType")).equals("2")) {
                tx = "max";
            } else if (CharUtil.null2Str(params.get("countType")).equals("3")) {
                tx = "min";
            }
            // 数据历史查询
            if (outFlag.equals("1")) {
                params.put("flag", 2);
                Workbook wb = ExcelUtil.fillExcelDataWithTemplate(
                        service.exportExcel(params, outFlag), "exportTemp.xls");
                SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(), params.get("menu_id").toString(), "6");  
                ResponseUtil.export(rep, wb, "数据查询导出.xls");
            }
            // 统计分析查询导出
            if (outFlag.equals("2")) {
                if (CharUtil.null2Str(params.get("sdw")).equals("1")) {
                    params.put("b_date", params.get("sdw2"));
                    params.put("e_date", params.get("sdw3"));
                    params.put("tx", tx);
                    List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                    list = UpToLow(params, listLow);
                }

                else if (CharUtil.null2Str(params.get("sdw")).equals("2")) {
                    params.put("a_date", params.get("sdw4"));
                    params.put("b_date", params.get("sdw5"));
                    params.put("e_date", params.get("sdw6"));
                    params.put("tx", tx);
                    for (int i = Integer.parseInt(params.get("sdw5").toString()); i <= Integer
                            .parseInt(params.get("sdw6").toString()); i++) {
                        params.put("para", i);
                        List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                        for (LinkedHashMap<String, Object> linkedHashMap : listLow) {
                            Iterator<String> iter = linkedHashMap.keySet().iterator();
                            HashMap<String, Object> map = new HashMap<>();
                            while (iter.hasNext()) {
                                String key = iter.next().toString();
                                String key0 = key.toLowerCase();
                                map.put(key0, linkedHashMap.get(key));
                            }
                            list.add(map);
                        }
                    }
                }
                if (CharUtil.null2Str(params.get("sdw")).equals("3")) {
                    params.put("b_date", params.get("sdw7"));
                    params.put("e_date", params.get("sdw8"));
                    params.put("tx", tx);
                    List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                    list = UpToLow(params, listLow);
                } else if (CharUtil.null2Str(params.get("sdw")).equals("4")) {
                    params.put("b_date", params.get("sdw9"));
                    params.put("e_date", params.get("sdw10"));
                    params.put("tx", tx);
                    List<LinkedHashMap<String, Object>> listLow = service.queryForList1(params);
                    list = UpToLow(params, listLow);
                }
                Workbook wb = ExcelUtil.fillExcelDataWithTemplate(list, "exportTemp.xls");
                SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(), params.get("menu_id").toString(), "6");  
                ResponseUtil.export(rep, wb, "统计分析查询导出.xls");
                // 预警数据查询
            } else if (outFlag.equals("3")) {
                params.put("flag", 0);
                Workbook wb = ExcelUtil.fillExcelDataWithTemplate(
                        service.exportExcel(params, outFlag), "exportWarnTemp.xls");
                SystemCountService.OperationLog(Utils.getUUID(),params.get("shiro_user_id").toString(), params.get("shiro_menu_id").toString(), "6");  
                ResponseUtil.export(rep, wb, "预警数据查询.xls");
            }
            // 对比查询导出
            else if (outFlag.equals("4")) {
                List<LinkedHashMap<String, Object>> list0 = new<LinkedHashMap<String, Object>> ArrayList();
                params.put("flag", 2);
                List<LinkedHashMap<String, Object>> list1 = service.queryForList(params);
                list0.addAll(list1);
                params.put("flag", 3);
                List<LinkedHashMap<String, Object>> list2 = service.queryForList0(params);
                list0.addAll(list2);
                Workbook wb = ExcelUtil.fillExcelDataWithTemplate0(list0, list1, list2,
                        "exportTempCompare.xls");
                SystemCountService.OperationLog(Utils.getUUID(),params.get("user_id").toString(), params.get("menu_id").toString(), "6");  
                ResponseUtil.export(rep, wb, "数据查询对比导出.xls");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description:传感器工作状态
     * @param  rep
     * @param  request
     * @param  params
     * @method queryListState
     * @return String   
     * @throws
     */
    @RequestMapping(value = "queryListState")
    public void queryListState(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params,
            @RequestParam(value = "currentPage", defaultValue = "1") String currentPage,
            @RequestParam(value = "pageSize", defaultValue = "20") String pageSize) {

        try {
            String   menu_id= CharUtil.null2Str(params.get("menu_id"));
            String   user_id= CharUtil.null2Str(params.get("user_id"));
            params.put("currentPage", currentPage);
            params.put("pageSize", pageSize);
            List list = service.createQueryPageForState(params);
           // SystemCountService.OperationLog(Utils.getUUID(),user_id, menu_id, "4");            
            rep.getWriter().print(Utils.jsonListStr(list));
            
            
  
          
       
        } catch (Exception e) {
            e.printStackTrace();
        }
      

    }

    /**
     * 
     * @Description:  获取传感器类型列表
     * @method getType
     * @param  request
     * @param  response    
     * @return void    返回类型 
     * @throws Exception
     */

    @RequestMapping(value = "getType")
    public void getType(HttpServletRequest request, HttpServletResponse response) {
        try {
            LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("query", "%");
            params.put("page", "0");
            params.put("limit", "0");
            List list_type = service.queryTypeList(params);
            response.getWriter().print(Utils.jsonListStr(list_type));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
    * 
    * @Description: 获取种类列表
    * @method getPart
    * @param  request
    * @param  response  
    * @method getPart
    * @return void  返回类型 
    * @throws Exception
    */
    @RequestMapping(value = "getPart")
    public void getPart(HttpServletRequest request, HttpServletResponse response, String type_id) {
        try {
            LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("query", type_id);
            params.put("page", "0");
            params.put("limit", "0");
            List list_part = service.queryPartList(params);

            response.getWriter().print(Utils.jsonListStr(list_part));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /** 
     * @Description:  通过种类part_id查询条件来获取截面列表
     * @method getSection
     * @param  request
     * @param  response
     * @param  part_id    设定文件 
     * @return void    返回类型 
     * @throws Exception
     */
    @RequestMapping(value = "getSection")
    public void getSection(HttpServletRequest request, HttpServletResponse response,
            String part_id) {
        try {
            LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("query", part_id);
            params.put("page", "0");
            params.put("limit", "0");
            List list_section = service.querySectionList(params);

            response.getWriter().print(Utils.jsonListStr(list_section));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @Description: 通过种类section_id查询条件来获取传感器列表
     * @method getSensor
     * @param  request
     * @param  response
     * @param  section_id   
     * @return void    
     * @throws Exception
     */

    @RequestMapping(value = "getSensor")
    public void getSensor(HttpServletRequest request, HttpServletResponse response,
            String section_id) {
        try {
            LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
            params.put("query", section_id);
            params.put("s_section_id", section_id);
            params.put("flag", 1);
            List list_sensor = service.querySensorList(params);
            response.getWriter().print(Utils.jsonListStr(list_sensor));
        } catch (Exception e) { // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     *  对比加载更多页面
     * @method queryListPage
     * @param rep
     * @param request
     * @param params
     */
    @RequestMapping(value = "queryListPage0")
    public void queryListPage0(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) {
        try {

            // 当flag=1时查询分页数据
            params.put("flag", 1);
            List<LinkedHashMap<String, Object>> page_list = service.queryForList(params);
            params.put("flag", 1);
            List<LinkedHashMap<String, Object>> page_list0 = service.queryForList0(params);
            // 当flag=0时查询所有数据
            params.put("flag", 0);
            List<LinkedHashMap<String, Object>> listLow = service.queryForList(params);
            List<LinkedHashMap<String, Object>> listLow0 = service.queryForList0(params);
            int pageSize = Integer.parseInt(params.get("limit").toString());
            int allRow = listLow.size();
            int allRow0 = listLow0.size();
            int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow / pageSize + 1;
            int totalPage0 = allRow0 % pageSize == 0 ? allRow0 / pageSize : allRow0 / pageSize + 1;
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            // 构造json串，将page，totalPage回传ajax中
            JSONObject s = new JSONObject();
            s.put("page", Integer.parseInt(params.get("page").toString()));
            s.put("totalPage", totalPage);
            s.put("totalPage0", totalPage0);
            s.put("page_list", page_list);
            s.put("page_list0", page_list0);

            rep.getWriter().print(s.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  数据查询统计查询加载更多页面
     * @method queryListPage
     * @param rep
     * @param request
     * @param params
     */
    @RequestMapping(value = "queryListPage")
    public void queryListPage(HttpServletResponse rep, HttpServletRequest request,
            @RequestParam LinkedHashMap<String, Object> params) {
        try {
            // 当flag=1时查询分页数据
            params.put("flag", 1);
            System.err.println("page" + params.get("page").toString());
            List<LinkedHashMap<String, Object>> page_list = service.queryForList(params);
            // 当flag=0时查询分页数据
            params.put("flag", 0);
            List<LinkedHashMap<String, Object>> listLow = service.queryForList(params);
            int pageSize = Integer.parseInt(params.get("limit").toString());
            int allRow = listLow.size();
            int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow / pageSize + 1;
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            // 构造json串，将page，totalPage回传ajax中
            JSONObject s = new JSONObject();
            s.put("page", Integer.parseInt(params.get("page").toString()));
            s.put("totalPage", totalPage);
            s.put("page_list", page_list);
            rep.getWriter().print(s.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @Description: 统计查询 oracle字段大写变小写 
     * @param  params
     * @param  listLow
     * @return List<HashMap<String,Object>> 
     * @throws
     */
    public List<HashMap<String, Object>> UpToLow(LinkedHashMap<String, Object> params,
            List<LinkedHashMap<String, Object>> listLow) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        try {
            for (LinkedHashMap<String, Object> linkedHashMap : listLow) {
                Iterator<String> iter = linkedHashMap.keySet().iterator();
                HashMap<String, Object> map = new HashMap<>();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    String key0 = key.toLowerCase();
                    map.put(key0, linkedHashMap.get(key));
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
