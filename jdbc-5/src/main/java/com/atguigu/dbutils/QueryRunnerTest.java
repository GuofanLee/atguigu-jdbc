package com.atguigu.dbutils;

import com.atgui.druid.utils.JDBCUtils;
import com.atguigu.jdbc.bean.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 请填写类的描述
 *
 * @author GuofanLee
 * @date 2020-05-06 17:10
 */
public class QueryRunnerTest {

    /**
     * 使用 DBUtils 测试插入操作
     */
    @Test
    public void testInsert() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "insert into customers(name, email, birth) values(?, ?, ?)";
            int affectedRows = runner.update(connection, sql, "吴亦凡", "wyf@sb.com", "2020-02-02");
            System.out.println("受影响的行数：" + affectedRows);
        } catch (Exception e) {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 使用 DBUtils 查询一条记录，使用 BeanHandler 处理结果集
     */
    @Test
    public void testQuery1() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id = ?";
            Customer customer = runner.query(connection, sql, new BeanHandler<>(Customer.class), 24);
            System.out.println(customer);
        } catch (Exception e) {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 使用 DBUtils 查询一条记录，使用 MapHandler 处理结果集
     */
    @Test
    public void testQuery2() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id = ?";
            Map<String, Object> result = runner.query(connection, sql, new MapHandler(), 18);
            System.out.println(result);
        } catch (Exception e) {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 使用 DBUtils 查询多条记录，使用 BeanListHandler 处理结果集
     */
    @Test
    public void testBatchQuery1() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id < ?";
            List<Customer> customers = runner.query(connection, sql, new BeanListHandler<>(Customer.class), 10);
            customers.forEach(System.out::println);
        } catch (Exception e) {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 使用 DBUtils 查询多条记录，使用 MapListHandler 处理结果集
     */
    @Test
    public void testBatchQuery2() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customers where id < ?";
            List<Map<String, Object>> resultList = runner.query(connection, sql, new MapListHandler(), 10);
            resultList.forEach(System.out::println);
        } catch (Exception e) {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 使用 DBUtils 查询表中的记录数，使用 ScalarHandler 处理结果集
     */
    @Test
    public void testGetCount() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = JDBCUtils.getConnection();
            String sql = "select count(*) from customers";
            Long count = runner.query(connection, sql, new ScalarHandler<>());
            System.out.println(count);
        } catch (Exception e) {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

}
