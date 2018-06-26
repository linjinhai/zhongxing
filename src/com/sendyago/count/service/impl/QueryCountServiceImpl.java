/* ==============================================================
* $ID: Service.java, v1.0 2016/5/4 17:13:01 zq Exp $
* created: [2016-05-03 17:13:101] by zq
* ==============================================================
* 健康监测系统模块统计分析查询
* ==============================================================
* Copyright (c) 哈尔滨工大云帆智慧信息技术有限公司 All rights reserved
* ==============================================================
*/
package com.sendyago.count.service.impl;

import com.sendyago.count.service.QueryCountService;
import com.sendyago.util.common.CharUtil;
import com.sendyago.util.common.PageBean;
import com.sendyago.util.common.Utils;
import com.sendyago.util.jdbc.OracleJDBC;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 描述类
 * 描述类声明方法
 * @author  $Author: zq&    
 * @version $Revision:1.0 2016年5月3日 下午5:15:46 $
 *
 */
@Component
public class QueryCountServiceImpl extends OracleJDBC implements QueryCountService {

    /** 
     * 历史查询数据列表
     * @return  数据列表结果集
     * @throws Exception
     */
    public List queryForList(LinkedHashMap<String, Object> param) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("s_sensor", param.get("s_sensor"));
        map.put("b_date", param.get("b_date"));
        map.put("e_date", param.get("e_date"));
        map.put("b_data", param.get("b_data"));
        map.put("e_data", param.get("e_data"));
        map.put("page", param.get("page"));
        map.put("limit", param.get("limit"));
        map.put("flag", param.get("flag"));
        List list = procedures4Query(map, "COUNT_QUERY_DATA_LIST");
        return list;
    }

    /**
     * 对比查询数据列表
     * @return  数据列表结果集
     * @throws Exception
     */
    public List queryForList0(LinkedHashMap<String, Object> param) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
    
        map.put("s_sensor0", param.get("s_sensor0"));
        map.put("b_date", param.get("b_date"));
        map.put("e_date", param.get("e_date"));
        map.put("b_data", param.get("b_data0"));
        map.put("e_data", param.get("e_data0"));
        map.put("page", param.get("page"));
        map.put("limit", param.get("limit"));
        map.put("flag", param.get("flag"));
        List list = procedures4Query(map, "COUNT_QUERY_DATA_LIST");
        return list;
    }

    /**
    * 查询分析数据列表
    * @return  数据列表结果集
    * @throws Exception
    */
    public List queryForList1(LinkedHashMap<String, Object> param) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("s_sensor", param.get("s_sensor"));
        map.put("a_date", param.get("a_date"));
        map.put("b_date", param.get("b_date"));
        map.put("e_date", param.get("e_date"));
        map.put("b_data", param.get("b_data"));
        map.put("e_data", param.get("e_data"));
        map.put("tx", param.get("tx"));
        map.put("sdw", param.get("sdw"));
        map.put("para", param.get("para"));
        List list = procedures4Query(map, "COUNT_QUERY_ANALYSIS_DATA_LIST");
        return list;
    }

    /**
     * 根据条件属性查询记录 传感器名称
     * @param params 查询条件
     * @return 单条记录
     * @throws Exception
     */
    public Map querySensorByInfo(LinkedHashMap<String, Object> params) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("s_sensor", params.get("s_sensor")); // 查询条件
        List list = procedures4Query(map, "COUNT_SINGLE_SENSOR_INFO");
        Map map0 = null;
        if (list.size() > 0) {
            return (Map) list.get(0);
        }
        return map0;

    }

    /**
     * 根据条件属性查询记录 传感器名称
     * @param params 查询条件
     * @return 单条记录
     * @throws Exception
     */
    public Map<Object, String> queryForObject(LinkedHashMap<String, Object> params)
            throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("s_sensor", params.get("s_sensor")); // 查询条件
        List list = procedures4Query(map, "COUNT_SINGLE_SENSOR_INFO");
        if (list.size() > 0) {
            return (Map<Object, String>) list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询检测类型数据列表
     * @param params 查询条件
     * @return  数据列表结果集
     * @throws Exception
     */
    public List queryTypeList(LinkedHashMap<String, Object> params) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("query", params.get("query"));
        map.put("page", params.get("page"));
        map.put("limit", params.get("limit"));
        List list = procedures4Query(map, "COUNT_SENSOR_TYPE_LIST");
        return list;
    }

    /**
     * 查询种类数据列表
     * @param params 查询条件
     * @return  数据列表结果集
     * @throws Exception
     */
    public List queryPartList(LinkedHashMap<String, Object> params) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("query", params.get("query"));
        map.put("page", params.get("page"));
        map.put("limit", params.get("limit"));
        List list = procedures4Query(map, "COUNT_SENSOR_PART_LIST");
        return list;
    }

    /**
     * 查询截面数据列表
     * @param params 查询条件
     * @return  数据列表结果集
     * @throws Exception
     */
    public List querySectionList(LinkedHashMap<String, Object> params) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("query", params.get("query"));
        map.put("page", params.get("page"));
        map.put("limit", params.get("limit"));
        List list = procedures4Query(map, "COUNT_SENSOR_SECTION_LIST");
        return list;
    }

    /**
     * 查询实时数据传感器数据列表
     * @param params 查询条件
     * @return  数据列表结果集
     * @throws Exception
     */
    public List querySensorList(LinkedHashMap<String, Object> params) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("s_section_id", params.get("s_section_id"));

        map.put("flag", params.get("flag"));
        List list = procedures4Query(map, "COUNT_UPDATE_SENSOR_LIST");
        return list;
    }

    /**
     * 历史数据查询,对比数据查询、预警查询、统计查询导出
     * @return  数据列表结果集
     * @throws Exception
     */
    public java.sql.ResultSet exportExcel(LinkedHashMap<String, Object> param, String outFlag)
            throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        java.sql.ResultSet rs = null;
        // 历史数据查询、对比查询导出
        if (outFlag.equals("1")) {
            map.put("s_sensor", param.get("s_sensor"));
            map.put("b_date", param.get("b_date"));
            map.put("e_date", param.get("e_date"));
            map.put("b_data", param.get("b_data"));
            map.put("e_data", param.get("e_data"));
            map.put("page", param.get("page"));
            map.put("limit", param.get("limit"));
            map.put("flag", param.get("flag"));
            rs = procedures5Query(map, "COUNT_QUERY_DATA_LIST");
        }
        // 统计分析导出
        else if (outFlag.equals("2")) {
            map.put("s_sensor", param.get("s_sensor"));
            map.put("a_date", param.get("a_date"));
            map.put("b_date", param.get("b_date"));
            map.put("e_date", param.get("e_date"));
            map.put("b_data", param.get("b_data"));
            map.put("e_data", param.get("e_data"));
            map.put("tx", param.get("tx"));
            map.put("sdw", param.get("sdw"));
            map.put("para", param.get("para"));
            rs = procedures5Query(map, "COUNT_QUERY_ANALYSIS_DATA_LIST");

        }
        // 预警查询导出
        else if (outFlag.equals("3")) {
            StringBuffer sb = new StringBuffer();
            sb.setLength(0);
            sb.append("where 1=1");
            if (!CharUtil.null2Str(param.get("checkType")).equals("")) {
                sb.append(" and c.type_id='" + param.get("checkType") + "'");
            }

            if (!CharUtil.null2Str(param.get("s_sensor")).equals("")) {
                sb.append(" and b.sensor_code='" + param.get("s_sensor") + "'");
            }

            if (!CharUtil.null2Str(param.get("b_date")).equals("")) {
                sb.append(" and warning_time > to_date('" + CharUtil.null2Str(param.get("b_date"))
                        + "','yyyy-mm-dd hh24:mi:ss') ");
            }

            if (!CharUtil.null2Str(param.get("e_date")).equals("")) {
                sb.append(" and warning_time <to_date('" + CharUtil.null2Str(param.get("e_date"))
                        + "','yyyy-mm-dd hh24:mi:ss') ");
            }

            if (CharUtil.null2Str(param.get("e_date")).equals("")
                    && CharUtil.null2Str(param.get("b_date")).equals("")
                    && CharUtil.null2Str(param.get("checkType")).equals("")
                    && CharUtil.null2Str(param.get("s_sensor")).equals("")) {

                sb.append(
                        " and to_date(to_char(warning_time,'yyyy-mm-dd'),'yyyy-mm-dd')=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')");

            }

            map.put("s_sensor", param.get("s_sensor"));
            map.put("flag", 0);
            map.put("b_date", param.get("b_date"));
            map.put("e_date", param.get("e_date"));
            map.put("b_data", param.get("b_data"));
            map.put("e_data", param.get("e_data"));
            map.put("v_cond", sb.toString());
            map.put("page", param.get("currentPage"));
            map.put("limit", param.get("pageSize"));
            rs = procedures5Query(map, "COUNT_WARN_SENSOR_LIST");

        }
        return rs;
    }

    /**
    *@param params
    *@return PageBean
    * 
    */
    public PageBean createQueryPageForWarn(LinkedHashMap<String, Object> params) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        sb.append("where 1=1");
        if (!CharUtil.null2Str(params.get("checkType")).equals("")) {
            sb.append(" and c.type_id='" + params.get("checkType") + "'");
        }

        if (!CharUtil.null2Str(params.get("s_sensor")).equals("")) {
            sb.append(" and b.sensor_code='" + params.get("s_sensor") + "'");
        }

        if (!CharUtil.null2Str(params.get("b_date")).equals("")) {
            sb.append(" and warning_time > to_date('" + CharUtil.null2Str(params.get("b_date"))
                    + "','yyyy-mm-dd hh24:mi:ss') ");
        }

        if (!CharUtil.null2Str(params.get("e_date")).equals("")) {
            sb.append(" and warning_time <to_date('" + CharUtil.null2Str(params.get("e_date"))
                    + "','yyyy-mm-dd hh24:mi:ss') ");
        }

        if (CharUtil.null2Str(params.get("e_date")).equals("")
                && CharUtil.null2Str(params.get("b_date")).equals("")
                && CharUtil.null2Str(params.get("checkType")).equals("")
                && CharUtil.null2Str(params.get("s_sensor")).equals("")) {
            if (CharUtil.null2Str(params.get("viewFlag")).equals("")) {
                sb.append(" and to_date(to_char(warning_time,'yyyy-mm-dd'),'yyyy-mm-dd')=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')");
            } else {
                sb.append(" ");
            }
        }

     
        map.put("s_sensor", params.get("s_sensor"));
        map.put("flag", params.get("flag"));
        map.put("b_date", params.get("b_date"));
        map.put("e_date", params.get("e_date"));
        map.put("b_data", params.get("b_data"));
        map.put("e_data", params.get("e_data"));
        map.put("v_cond", sb.toString());
        map.put("page", params.get("currentPage"));
        map.put("limit", params.get("pageSize"));
        // 分页list
        List list = procedures4Query(map, "COUNT_WARN_SENSOR_LIST");

        // 获取总数量
        map.put("flag", 0);
        List list_count = procedures4Query(map, "COUNT_WARN_SENSOR_LIST");

        // 构造返回pageBean
        // 传参页数 page，每页条数 limit、分页记录list、map属性params、
        return Utils.getPageBean(Integer.parseInt(map.get("limit").toString()),
                Integer.parseInt(map.get("page").toString()), list, params, list_count.size());
    }

    public Map queryLineWarn(LinkedHashMap<String, Object> params) throws Exception {
        String warning_time = params.get("warning_time").toString();
        params.remove("warning_time");
        // LinkedHashMap<String, Object> map = new LinkedHashMap<String,
        // Object>();
        Map map = querySensorByInfo(params);
        /**
         * 根据时间和传感器查询报警信息
         * 查询报警时间前后10分钟数据
         * 先将报警时间转换毫秒，在计算前后时间点
         */
        long warn_mills = Utils.getMillis2(warning_time);
        long tenmins = 60 * 10 * 1000;
        // 获取前10分钟日期
        String start_date = Utils.getDateByMillis(warn_mills - tenmins);
        // 获取后10分钟日期
        String end_date = Utils.getDateByMillis(warn_mills + tenmins);
        params.put("s_sensor", params.get("s_sensor"));
        params.put("flag", 2);
        params.put("b_date", start_date);
        params.put("e_date", end_date);
        params.put("b_data", params.get("b_data"));
        params.put("e_data", params.get("e_data"));
        params.put("v_cond", "");
        params.put("page", params.get("currentPage"));
        params.put("limit", params.get("pageSize"));
        List list = procedures4Query(params, "COUNT_WARN_SENSOR_LIST");
        map.put("list", list);

        // TODO Auto-generated method stub
        return map;
    }

    /**
     *功能 传感器工作状态分页列表
     *@param params
     *@return List
     * 
     */
    public List createQueryPageForState(LinkedHashMap<String, Object> params) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        sb.append("where 1=1");
        if (!CharUtil.null2Str(params.get("checkType")).equals("")) {
            sb.append(" and c.type_id='" + params.get("checkType") + "'");
        }

        if (!CharUtil.null2Str(params.get("s_sensor")).equals("")) {
            sb.append(" and b.sensor_code='" + params.get("s_sensor") + "'");
        }

        if (CharUtil.null2Str(params.get("checkType")).equals("")
                && CharUtil.null2Str(params.get("s_sensor")).equals("")) {

            sb.append("");

        }

        map.put("s_sensor", params.get("s_sensor"));
        map.put("flag", 0);
        map.put("b_date", params.get("b_date"));
        map.put("e_date", params.get("e_date"));
        map.put("b_data", params.get("b_data"));
        map.put("e_data", params.get("e_data"));
        map.put("v_cond", sb.toString());
        map.put("page", "1");
        map.put("limit", "1");

        List list = procedures4Query(map, "COUNT_STATE_SENSOR_LIST");
        return list;


    }
}
