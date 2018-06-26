/* ==============================================================
 * $ID: OracleJDBC.java, v1.1 2016/5/20 09:20:00 Rick Exp $
 * created: [2016-04-27 14:20:00] by Rick
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 数据库连接类
 *
 * @author $Author: Rick$
 * @version $Revision: 1.0 $Date: 2016/4/27 14:20:00$
 * @version $Revision: 1.1 $Date: 2016/5/20 09:24:00  去掉Connection初始化时的static关键字 $
 * @version $Revision: 1.2 $Date: 2016/5/20 09:24:00  优化数据库连接 $
 */
public class JDBCConnect {

    private Connection conn;
    private int connCount = 0; // 连接数据库的次数
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
    // 获得数据库连接信息
    private String driver = resourceBundle.getString("jdbc.driverClassName");
    private String url = resourceBundle.getString("jdbc.url");
    private String userName = resourceBundle.getString("jdbc.username");
    private String password = resourceBundle.getString("jdbc.password");

    private static Log log = LogFactory.getLog(JDBCConnect.class);

    // 每连接一次,连接次数加1
    private void connCountPlus() {
        ++ this.connCount;
    }

    // 连接次数减1
    private void connCountReduce() {
        if(this.connCount > 0) {
            --this.connCount;
        }
    }

    /**
     * 初始化数据库连接
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        // 首先判断数据库是否已经连接
        if(null != conn && !conn.isClosed()) {
            connCountPlus(); // 连接次数加1
            return conn;
        }
        // 数据库连接
        Class.forName(driver);
        conn = DriverManager.getConnection(url, userName, password);
        if(!conn.isClosed()) {
            connCountPlus(); // 连接次数加1
            log.error("=========SUCCESS: 数据库已连接!=========");
        }else {
            log.error("=========ERROR: 数据库连接失败!=========");
        }
        return conn;
    }

    /**
     * 关闭连接
     */
    public void closeConnection() {
        try {
            // 每断开一次连接,连接次数次1
            connCountReduce();

            // 直接连接次数为0时,关闭连接
            if(null != conn && this.connCount == 0) {
                conn.close();
                log.debug("=========CLOSED: 已断开数据库连接!=========");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
