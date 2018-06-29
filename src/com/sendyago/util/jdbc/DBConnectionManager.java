/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sendyago.util.jdbc;


/**
 * 数据库连接池,当前数据库为oracle
 * @author rick
 * @vertion 1.0
 * 注:本连接池代码主要源自网络
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a Singleton that provides access to one or many connection
 * pools defined in a Property file. A client gets access to the single instance
 * through the static getInstance() method and can then check-out and check-in
 * connections from YUCI.a pool. When the client shuts down it should call the
 * release() method to close all open connections and do other clean up.
 */
public class DBConnectionManager {

    static private DBConnectionManager instance;       // The single instance
    static private int clients;
    private Vector drivers = new Vector();
    private static Log log = LogFactory.getLog(DBConnectionManager.class);
    private Hashtable pools = new Hashtable();

    /**
     * Returns the single instance, creating one if it's the first time this
     * method is called.
     *
     * @return DBConnectionManager The single instance.
     */
    static synchronized public DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        clients++;
        return instance;
    }

    /**
     * A private constructor since this is a Singleton
     */
    private DBConnectionManager() {
        init();
    }

    /**
     * Returns a connection to the named pool.
     *
     * @param name The pool name as defined in the properties file
     * @param con The Connection
     */
    public void freeConnection(String name, Connection con) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null) {
            pool.freeConnection(con);
        }
    }

    /**
     * Returns an open connection. If no one is available, and the max number of
     * connections has not been reached, a new connection is created.
     *
     * @param name The pool name as defined in the properties file
     * @return Connection The connection or null
     */
    public java.sql.Connection getConnection(String name) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null) {
            return pool.getConnection();
        }
        return null;
    }

    /**
     * Returns an open connection. If no one is available, and the max number of
     * connections has not been reached, a new connection is created. If the max
     * number has been reached, waits until one is available or the specified
     * time has elapsed.
     *
     * @param name The pool name as defined in the properties file
     * @param time The number of milliseconds to wait
     * @return Connection The connection or null
     */
    public java.sql.Connection getConnection(String name, long time) {
        DBConnectionPool pool = (DBConnectionPool) pools.get(name);
        if (pool != null) {
            return pool.getConnection(time);
        }
        return null;
    }

    /**
     * Closes all open connections and deregisters all drivers.
     */
    public synchronized void release() {
        // Wait until called by the last client
        if (--clients != 0) {
            return;
        }

        Enumeration allPools = pools.elements();
        while (allPools.hasMoreElements()) {
            DBConnectionPool pool = (DBConnectionPool) allPools.nextElement();
            pool.release();
        }
        Enumeration allDrivers = drivers.elements();
        while (allDrivers.hasMoreElements()) {
            Driver driver = (Driver) allDrivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log("Deregistered JDBC driver " + driver.getClass().getName());
            } catch (SQLException e) {
                log(e, "Can't deregister JDBC driver: " + driver.getClass().getName());
            }
        }
    }

    /**
     * Creates instances of DBConnectionPool based on the properties. A
     * DBConnectionPool can be defined with the following properties:
     * <PRE>
     * <poolname>.url         The JDBC URL for the database
     * <poolname>.user        A database user (optional)
     * <poolname>.password    A database user password (if user specified)
     * <poolname>.maxconn     The maximal number of connections (optional)
     * </PRE>
     *
     * @param resourceBundle The connection pool properties
     */
    private void createPools(ResourceBundle resourceBundle) {
        String name = "jdbc.url";
        if (name.endsWith(".url")) {
            String poolName = name.substring(0, name.lastIndexOf("."));
            String url = resourceBundle.getString(poolName + ".url");
            if (url == null) {
                log("No URL specified for " + poolName);
            }
            String user = resourceBundle.getString(poolName + ".username");
            String password = resourceBundle.getString(poolName + ".password");
            String maxconn = resourceBundle.getString(poolName + ".maxconn");
            int max;
            try {
                max = Integer.valueOf(maxconn).intValue();
            } catch (NumberFormatException e) {
                log("Invalid maxconn value " + maxconn + " for " + poolName);
                max = 0;
            }
            DBConnectionPool pool =
                    new DBConnectionPool(poolName, url, user, password, max);
            pools.put(poolName, pool);
            log("Initialized pool " + poolName);
        }
    }

    /**
     * Loads properties and initializes the instance with its values.
     */
    private void init() {
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle("jdbc");
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return;
        }

        loadDrivers(resourceBundle);
        createPools(resourceBundle);
    }

    /**
     * Loads and registers all JDBC drivers. This is done by the
     * DBConnectionManager, as opposed to the DBConnectionPool, since many pools
     * may share the same driver.
     *
     * @param resourceBundle The connection pool properties
     */
    private void loadDrivers(ResourceBundle resourceBundle) {
        String driverClasses = resourceBundle.getString("jdbc.driverClassName");
        StringTokenizer st = new StringTokenizer(driverClasses);
        while (st.hasMoreElements()) {
            String driverClassName = st.nextToken().trim();
            try {
                Driver driver = (Driver) Class.forName(driverClassName).newInstance();
                DriverManager.registerDriver(driver);
                drivers.addElement(driver);
                log("Registered JDBC driver " + driverClassName);
            } catch (Exception e) {
                log("Can't register JDBC driver: "
                        + driverClassName + ", Exception: " + e);
            }
        }
    }

    /**
     * Writes a message to the log file.
     */
    private void log(String msg) {
        log.info(new Date() + ": " + msg);
    }

    /**
     * Writes a message with an Exception to the log file.
     */
    private void log(Throwable e, String msg) {
        log.error(new Date() + ": " + msg);
//        e.printStackTrace(log);
    }

    /**
     * This inner class represents a connection pool. It creates new connections
     * on demand, up to a max number if specified. It also makes sure a
     * connection is still open before it is returned to a client.
     */
    class DBConnectionPool {

        private int checkedOut;
        private Vector freeConnections = new Vector();
        private int maxConn;
        private String name;
        private String password;
        private String URL;
        private String user;

        /**
         * Creates new connection pool.
         *
         * @param name The pool name
         * @param URL The JDBC URL for the database
         * @param user The database user, or null
         * @param password The database user password, or null
         * @param maxConn The maximal number of connections, or 0 for no limit
         */
        public DBConnectionPool(String name, String URL, String user, String password,
                                int maxConn) {
            this.name = name;
            this.URL = URL;
            this.user = user;
            this.password = password;
            this.maxConn = maxConn;
        }

        /**
         * Checks in a connection to the pool. Notify other Threads that may be
         * waiting for a connection.
         *
         * @param con The connection to check in
         */
        public synchronized void freeConnection(Connection con) {
            // Put the connection at the end of the Vector
            freeConnections.addElement(con);
            checkedOut--;
            notifyAll();
        }

        /**
         * Checks out a connection from YUCI.the pool. If no free connection is
         * available, a new connection is created unless the max number of
         * connections has been reached. If a free connection has been closed by
         * the database, it's removed from YUCI.the pool and this method is called
         * again recursively.
         */
        public synchronized java.sql.Connection getConnection() {
            java.sql.Connection con = null;
            if (freeConnections.size() > 0) {
                // Pick the first Connection in the Vector
                // to get round-robin usage
                con = (java.sql.Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        log("Removed bad connection from YUCI." + name);
                        // Try again recursively
                        con = getConnection();
                    }
                } catch (SQLException e) {
                    log("Removed bad connection from YUCI." + name);
                    // Try again recursively
                    con = getConnection();
                }
            } else if (maxConn == 0 || checkedOut < maxConn) {
                con = newConnection();
            }
            if (con != null) {
                checkedOut++;
            }
            return con;
        }

        /**
         * Checks out a connection from YUCI.the pool. If no free connection is
         * available, a new connection is created unless the max number of
         * connections has been reached. If a free connection has been closed by
         * the database, it's removed from YUCI.the pool and this method is called
         * again recursively. <P> If no connection is available and the max
         * number has been reached, this method waits the specified time for one
         * to be checked in.
         *
         * @param timeout The timeout value in milliseconds
         */
        public synchronized java.sql.Connection getConnection(long timeout) {
            long startTime = new Date().getTime();
            java.sql.Connection con;
            while ((con = getConnection()) == null) {
                try {
                    wait(timeout);
                } catch (InterruptedException e) {
                }
                if ((new Date().getTime() - startTime) >= timeout) {
                    // Timeout has expired
                    return null;
                }
            }
            return con;
        }

        /**
         * Closes all available connections.
         */
        public synchronized void release() {
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                java.sql.Connection con = (java.sql.Connection) allConnections.nextElement();
                try {
                    con.close();
                    log("Closed connection for pool " + name);
                } catch (SQLException e) {
                    log(e, "Can't close connection for pool " + name);
                }
            }
            freeConnections.removeAllElements();
        }

        /**
         * Creates a new connection, using a userid and password if specified.
         */
        private java.sql.Connection newConnection() {
            java.sql.Connection con = null;
            try {
                if (user == null) {
                    con = DriverManager.getConnection(URL);
                } else {
                    con = DriverManager.getConnection(URL, user, password);
                }
                log("Created a new connection in pool " + name);
            } catch (SQLException e) {
                log(e, "Can't create a new connection for " + URL);
                return null;
            }
            return con;
        }
    }

}