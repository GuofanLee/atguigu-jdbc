package com.atguigu.jdbc.utils;

import com.atguigu.jdbc.bean.Customer;
import com.atguigu.jdbc.bean.Order;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用查询操作模板
 *
 * @author GuofanLee
 * @date 2020-05-03 16:11
 */
public class QueryTemplate {

    /**
     * 查询一条记录测试
     */
    @Test
    public void test1() {
        String sql = "select id, name, email, birth from customers where id = ?";
        Customer customer = queryOne(Customer.class, sql, 10);
        System.out.println(customer);
        System.out.println("==============================================================");
        String sql2 = "select order_id orderId, order_name orderName, order_date orderDate from `order` where order_id = ?";
        Order order = queryOne(Order.class, sql2, 1);
        System.out.println(order);
    }

    /**
     * 查询多条记录测试
     */
    @Test
    public void test2() {
        String sql = "select id, name, email, birth from customers";
        List<Customer> customerList = queryList(Customer.class, sql);
        customerList.forEach(System.out::println);
        System.out.println("==============================================================");
        String sql2 = "select order_id orderId, order_name orderName, order_date orderDate from `order`";
        List<Order> orderList = queryList(Order.class, sql2);
        orderList.forEach(System.out::println);
    }

    /**
     * 查询多条记录模板
     */
    public static <T> List<T> queryList(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> result = new ArrayList<>();
        try {
            //1.获取数据库连接
            connection = JDBCUtils.getConnection();
            //2.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4.执行SQL语句，并接收结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            //5.处理结果集
            while (resultSet.next()) { //判断结果集中是否有数据，如果有数据返回true，并将指针下移；否则返回false
                T t = clazz.newInstance();
                //处理结果集中的一行数据
                for (int i = 0; i < columnCount; i++) {
                    //获取每列的列名/别名（有别名获取的就是别名，无别名获取的就是列名）
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //获取每列的值
                    Object columnValue = resultSet.getObject(i + 1);
                    //通过反射给对象赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                result.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.关闭数据库资源
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return result;
    }

    /**
     * 查询一条记录模板
     */
    public static <T> T queryOne(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T t = null;
        try {
            //1.获取数据库连接
            connection = JDBCUtils.getConnection();
            //2.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4.执行SQL语句，并接收结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            //5.处理结果集
            if (resultSet.next()) { //判断结果集中是否有数据，如果有数据返回true，并将指针下移；否则返回false
                t = clazz.newInstance();
                //处理结果集中的一行数据
                for (int i = 0; i < columnCount; i++) {
                    //获取每列的列名/别名（有别名获取的就是别名，无别名获取的就是列名）
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //获取每列的值
                    Object columnValue = resultSet.getObject(i + 1);
                    //通过反射给对象赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.关闭数据库资源
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return t;
    }

}
