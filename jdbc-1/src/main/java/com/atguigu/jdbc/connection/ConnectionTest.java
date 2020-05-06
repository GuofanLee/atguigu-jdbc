package com.atguigu.jdbc.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * 加载/注册驱动，获取连接
 * 此处仅对几种方式进行演示，最终使用见 com.atguigu.utils.JDBCUtils
 *
 * @author GuofanLee
 * @date 2020-05-01 23:41
 */
public class ConnectionTest {

    /**
     * 方式一：最原始的方法
     */
    @Test
    public void testConnection1() throws Exception {
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/atguigu_jdbc";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    /**
     * 方式二：使用反射对方式一进行改进
     */
    @Test
    public void testConnection2() throws Exception {
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        String url = "jdbc:mysql://localhost:3306/atguigu_jdbc";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    /**
     * 方式三：使用DriverManager管理数据库驱动
     */
    @Test
    public void testConnection3() throws Exception {
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();
        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://localhost:3306/atguigu_jdbc";
        String user = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    /**
     * 方式四：自动注册驱动
     * com.mysql.jdbc.Driver类在加载的时候，会通过静态代码块将mysql驱动自动注册到DriverManager中
     */
    @Test
    public void testConnection4() throws Exception {
        //com.mysql.jdbc.Driver类在加载的时候，会通过静态代码块将mysql驱动自动注册到DriverManager中
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/atguigu_jdbc";
        String user = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    /**
     * 方式五：使用配置文件加载配置
     */
    @Test
    public void testConnection5() throws Exception {
        //读取配置，ConnectionTest为当前方法所在的类名
//        InputStream resource = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        InputStream resource = ClassLoader.getSystemResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(resource);
        String driverClass = properties.getProperty("driverClass");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        //注册驱动
        Class.forName(driverClass);
        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

}
