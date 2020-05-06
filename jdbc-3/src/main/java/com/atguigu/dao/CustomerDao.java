package com.atguigu.dao;

import com.atguigu.jdbc.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * 此接口用于规范陈对于 customers 表的增、删、改、查操作
 *
 * @author GuofanLee
 * @date 2020-05-05 20:47
 */
public interface CustomerDao {

    /**
     * 向 customers 表中添加一条数据
     */
    void insert(Connection connection, Customer customer);

    /**
     * 根据 id 删除一条数据
     */
    void deleteById(Connection connection, int id);

    /**
     * 根据 id 修改一条记录
     */
    void updateById(Connection connection, Customer customer);

    /**
     * 根据 id 查询一条记录
     */
    Customer getById(Connection connection, int id);

    /**
     * 查询所有记录
     */
    List<Customer> getAll(Connection connection);

    /**
     * 查询总记录数
     */
    Long getCount(Connection connection);

    /**
     * 查询最大的生日
     */
    Date getMaxBirth(Connection connection);

}
