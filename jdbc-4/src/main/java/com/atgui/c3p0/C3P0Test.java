package com.atgui.c3p0;

import com.atgui.c3p0.utils.JDBCUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 请填写类的描述
 *
 * @author GuofanLee
 * @date 2020-05-06 01:01
 */
public class C3P0Test {

    @Test
    public void test1() throws Exception {
        //创建c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        //设置数据库基本信息
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/atguigu_jdbc");
        cpds.setUser("root");
        cpds.setPassword("123456");
        //设置初始连接数
        cpds.setInitialPoolSize(5);
        //设置最大连接数
        cpds.setMaxPoolSize(10);
        //设置其它参数......
        //获取连接
        Connection connection = cpds.getConnection();
        System.out.println(connection);
        //销毁C3P0数据库连接池，一般不会主动销毁
        //DataSources.destroy(cpds);
    }

    @Test
    public void test2() throws SQLException {
        //传入的参数与配置文件中 named-config 标签的 name 属性一致
        DataSource dataSource = new ComboPooledDataSource("c3p0-config");
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void test3() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);
    }

}
