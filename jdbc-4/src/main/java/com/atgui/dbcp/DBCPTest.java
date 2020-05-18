package com.atgui.dbcp;

import com.atgui.dbcp.utils.JdbcUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 请填写类的描述
 *
 * @author GuofanLee
 * @date 2020-05-06 14:40
 */
public class DBCPTest {

    @Test
    public void test1() throws SQLException {
        //创建DBCP数据库连接池
        BasicDataSource source = new BasicDataSource();
        //设置数据库基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/atguigu_jdbc");
        source.setUsername("root");
        source.setPassword("123456");
        //设置初始连接数
        source.setInitialSize(5);
        //设置最小连接数
        source.setMinIdle(10);
        //设置最大连接数
        source.setMaxActive(20);
        //设置其它参数......
        //获取连接
        Connection connection = source.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test2() throws Exception {
        Properties properties = new Properties();
        InputStream resource = ClassLoader.getSystemResourceAsStream("dbcp.properties");
        properties.load(resource);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test3() throws SQLException {
        System.out.println(JdbcUtils.getConnection());
    }

}
