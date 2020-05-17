package com.atguigu.jdbc.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC工具类
 *
 * @author GuofanLee
 * @date 2020-05-03 11:03
 */
public class JdbcUtils {

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws Exception {
        Properties properties = new Properties();
        //InputStream resource = ClassLoader.getSystemResourceAsStream("jdbc.properties");
        //InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        //Java web 项目中必须这么写，否则返回的 InputStream 为 null
        InputStream resource = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(resource);
        String driverClass = properties.getProperty("driverClass");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        //注册驱动
        Class.forName(driverClass);
        //获取连接
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭数据库资源，注意关闭的顺序：后打开的先关闭，先打开的后关闭
     */
    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
