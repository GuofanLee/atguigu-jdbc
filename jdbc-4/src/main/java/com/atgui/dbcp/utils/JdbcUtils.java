package com.atgui.dbcp.utils;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 使用DBCP数据库连接池获取数据库连接
 *
 * @author GuofanLee
 * @date 2020-05-06 14:17
 */
public class JdbcUtils {

    private static DataSource dataSource;

    static {
        try {
            Properties properties = new Properties();
            //InputStream resource = ClassLoader.getSystemResourceAsStream("jdbc.properties");
            //InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            //Java web 项目中必须这么写，否则返回的 InputStream 为 null
            InputStream resource = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
            properties.load(resource);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用DBCP数据库连接池获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
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
