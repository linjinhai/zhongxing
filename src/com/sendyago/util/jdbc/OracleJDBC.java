/* ==============================================================
 * $ID: OracleJDBC.java, v1.6.1 2016/7/11 16:35:00 Rick Exp $
 * created: [2016-04-27 11:02:00] by Rick
 * ==============================================================
 * 健康监测系统模块化公共信息
 *
 * 健康监测系统模块化即将健康监测系统各功能模块进行项目化形式进行开发
 * 将各功能模块划分为各个WEB项目
 * ==============================================================
 * @copyright 哈尔滨工大云帆智慧信息技术有限公司
 * ==============================================================
 */
package com.sendyago.util.jdbc;

import com.sendyago.util.common.CharUtil;
import com.sendyago.util.context.CONFIG;
import oracle.jdbc.OracleTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.json.JSONArray;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * 调用数据库类
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/27 11:02:00$
 * @version $Revision: 1.1 $Date: 2016/4/28 13:13:00$
 * @version $Revision: 1.2 $Date: 2016/5/20 09:20:00 去掉ResultSet, CallableStatement初始化的关键字 $
 * @version $Revision: 1.3 $Date: 2016/5/20 16:13:00 方法加入synchronized关键字,解决数据库调用可以同步进行,非异步 $
 * @version $Revision: 1.4 $Date: 2016/5/23 10:31:00 修改存储过程传参的问题 $
 * @version $Revision: 1.5 $Date: 2016/6/22 11:20:00 复用procedures4Query()方法, 可返回不同类型结果 $
 * @version $Revision: 1.5.1 $Date: 2016/6/23 10:58:00 修改procedures4Query()方法错误处理 $
 * @version $Revision: 1.6 $Date: 2016/7/11 10:20:00 将数据库连接改为连接池形式 $
 * @version $Revision: 1.6.1 $Date: 2016/7/11 16:35:00 ResultSet使用后关闭 $
 */
public class OracleJDBC {

    private CallableStatement cs = null;

    private static Log log = LogFactory.getLog(OracleJDBC.class);

    // 用于拼接调用存储过程中的问号
    // @param num 参数个数
    // @return ?的个数,以逗号分隔
    private String setParamNum(int num) {
        StringBuffer params = new StringBuffer();
        if(num > 0) {
            for (int i=1; i<num; i++) {
                params.append("?,");
            }
            params.append("?");
        }
        return params.toString();
    }

    // 将参数传入被调用的存储过程
    private CallableStatement setParam(CallableStatement cst, LinkedHashMap map) {
        try {
            int count = 1; // 参数的数量
            if(null != map) {
                // 遍历传入的参数信息
                Iterator it = map.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    Object obj = map.get(key);
                    cst.setString(count, (CharUtil.null2Str(obj)).toString());
                    count++;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return cst;
    }

    // 遍历返回的结果集,并封装到list集合当中
    private List getList(CallableStatement cs, int count, int flag) {
        // 定义用于返回结果集的集合
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String, Object> value = new LinkedHashMap<String, Object>();
        try {
            switch (flag) {
                // 返回结果为集合
                case CONFIG.CURSOR:
                    ResultSet resultSet = (ResultSet) cs.getObject(count);
                    ResultSetMetaData md = resultSet.getMetaData(); //获得结果集结构信息,元数据
                    int columnCount = md.getColumnCount(); //获得列数
                    while (resultSet.next()) {
                        LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            rowData.put(md.getColumnName(i), resultSet.getObject(i));
                        }
                        list.add(rowData);
                    }
                    resultSet.close();
                    break;
                // 返回的结果为数字型
                case CONFIG.NUMBER:
                    BigDecimal data = (BigDecimal) cs.getObject(count);
                    double newData = data.doubleValue();
                    value.put(CONFIG.RESULT, newData);
                    list.add(value);
                    break;
                // 返回的结果为字符型
                case CONFIG.VARCHAR:
                    String str = (String) cs.getObject(count);
                    value.put(CONFIG.RESULT, str);
                    list.add(value);
                    break;
                default:
                    break;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 关闭statement
    private void closeCS() {
        try {
            if(null != this.cs) {
                this.cs.close();
                this.cs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的flag判断返回结果的类型
     * 调用数据库存储过程,并返回查询结果
     * @param map 传入的参数
     * @param proceduresName 调用的存储过程名称
     * @param flag  1:集合,2:int/double,3:string
     * @return 结果集
     */
    public synchronized List procedures4Query(LinkedHashMap<String, Object> map, String proceduresName, int flag) {
        // 打开数据库连接
        Connection conn = DBConnectionManager.getInstance().getConnection("jdbc");
        // 定义用于返回结果集的集合
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        try {
            if(!conn.isClosed()) {
                int count = 0;
                if(null!=map && map.size() > 0) {
                    count = map.size();
                }
                // 拼接调用参数中的问号
                String params = setParamNum(count + 1);
                String sql = "{call " + proceduresName + "(" + params.toString() + ")}";
                cs = conn.prepareCall(sql);
                cs = setParam(cs, map);

                log.error("##存储过程调用: " + sql);
                log.error("##参数: " + JSONArray.fromObject(map));

                switch (flag) {
                    case CONFIG.CURSOR :
                        cs.registerOutParameter(count+1, OracleTypes.CURSOR);
                        break;
                    case CONFIG.NUMBER :
                        cs.registerOutParameter(count+1, OracleTypes.NUMBER);
                        break;
                    case CONFIG.VARCHAR :
                        cs.registerOutParameter(count+1, OracleTypes.VARCHAR);
                        break;
                    default:
                        cs.registerOutParameter(count+1, OracleTypes.VARCHAR);
                        break;
                }

                cs.execute(); // 执行存储过程调用

                list = getList(cs, count+1, flag);
            } // end if
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCS();
            // 释放连接到连接池
            DBConnectionManager.getInstance().freeConnection("jdbc", conn);
        } // end try
        return list;
    }

    /**
     * 调用数据库存储过程,并返回查询结果集
     * @param map 传入的参数
     * @param proceduresName 调用的存储过程名称
     * @return 结果集
     */
    public synchronized List procedures4Query(LinkedHashMap<String, Object> map, String proceduresName) {
        // 定义用于返回结果集的集合
        List<LinkedHashMap<String, Object>> list = this.procedures4Query(map, proceduresName, CONFIG.CURSOR);
        return list;
    }

    /**
     * 调用存储过程,用于保存/更新/删除信息
     * @param map 要保存/更新/删除的数据
     * @param proceduresName 存储过程名称
     */
    public synchronized void procedures4Change(LinkedHashMap<String, Object> map, String proceduresName) {
        // 打开数据库连接
        Connection conn = DBConnectionManager.getInstance().getConnection("jdbc");
        try {
            if(!conn.isClosed()) {
                int count = 0;
                if(null!=map && map.size() > 0) {
                    count = map.size();
                }
                // 拼接调用参数中的问号
                String params = setParamNum(count);
                String sql = "{call " + proceduresName + "(" + params.toString() + ")}";
                cs = conn.prepareCall(sql);
                cs = setParam(cs, map);
                log.error("##存储过程调用: " + sql);
                log.error("##参数: " + JSONArray.fromObject(map));
                cs.execute(); // 执行存储过程调用
            } // end if
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeCS();
            // 释放连接到连接池
            DBConnectionManager.getInstance().freeConnection("jdbc", conn);
        } // end try
    }

    /**
     *
     * @Description: TODO返回rs用于导出功能
     * @param @param map
     * @param @param proceduresName
     * @param @return    设定文件
     * @return ResultSet    返回类型
     * @throws
     */
    public ResultSet procedures5Query(LinkedHashMap<String, Object> map, String proceduresName) {
        // 打开数据库连接
        Connection conn = DBConnectionManager.getInstance().getConnection("jdbc");
        ResultSet rs = null;
        CallableStatement cs = null;
        try {
            if(!conn.isClosed()) {
                int count = 0;
                if(null!=map && map.size() > 0) {
                    count = map.size();
                }
                // 拼接调用参数中的问号
                String params = setParamNum(count + 1);
                String sql = "{call " + proceduresName + "(" + params.toString() + ")}";
                cs = conn.prepareCall(sql);
                cs = setParam(cs, map);
                cs.registerOutParameter(count+1, oracle.jdbc.OracleTypes.CURSOR);
                cs.execute(); // 执行存储过程调用
                rs = (ResultSet)cs.getObject(count+1);
                closeCS();

            } // end if
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
} // end class
