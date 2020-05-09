package com.atguigu.junit;

import com.atguigu.dao.CustomerDao;
import com.atguigu.dao.CustomerDaoImpl;
import com.atguigu.jdbc.bean.Customer;
import com.atguigu.jdbc.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 请填写类的描述
 *
 * @author GuofanLee
 * @date 2020-05-05 21:32
 */
public class CustomerDaoTest {

    CustomerDao customerDao = new CustomerDaoImpl();

    @Test
    public void testInsert() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            Customer customer = new Customer(null, "蔡徐坤", "cxk@sb.com", new Date(2020, Calendar.FEBRUARY, 2));
            customerDao.insert(connection, customer);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testDelete() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            customerDao.deleteById(connection, 23);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testUpdate() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            Customer customer = new Customer(24, "菜虚空", "cxk@sb.com", new Date(120, Calendar.FEBRUARY, 2));
            customerDao.updateById(connection, customer);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testGetById() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            Customer customer = customerDao.getById(connection, 24);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testGetAll() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            List<Customer> customers = customerDao.getAll(connection);
            customers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testGetCount() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            Long count = customerDao.getCount(connection);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testMaxBirth() {
        Connection connection = null;
        try {
            connection = JdbcUtils.getConnection();
            Date maxBirth = customerDao.getMaxBirth(connection);
            System.out.println(maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResource(connection, null, null);
        }
    }

}
