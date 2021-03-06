package com.atgui.c3p0.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 使用C3P0数据库连接池获取数据库连接
 *
 * @author GuofanLee
 * @date 2020-05-06 14:17
 */
public class JdbcUtils {

    //传入的参数与配置文件中 named-config 标签的 name 属性一致
    private static final DataSource dataSource = new ComboPooledDataSource("c3p0-config");

    /**
     * 使用C3P0数据库连接池获取数据库连接
     */
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    /**
     * 关闭数据库资源，注意关闭的顺序：后打开的先关闭，先打开的后关闭
     * 从连接池中获取的连接，已经重写了 close() 方法，不会真的关闭连接
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
