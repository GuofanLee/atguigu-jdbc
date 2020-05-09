package com.atguigu.dao;

import com.atguigu.jdbc.utils.JdbcUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装了对于数据表的通用操作
 *
 * @author GuofanLee
 * @date 2020-05-05 20:04
 */
public abstract class BaseDao<T> {

    private final Class<T> clazz;

    {
        //获取当前BaseDao的子类继承的带泛型的父类的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        //获取所有泛型参数
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        //获取第一个泛型参数
        clazz = (Class<T>) actualTypeArguments[0];
    }

    /**
     * 通用增、删、改、操作模板升级版，数据库连接 Connection 由调用者管理，以实现事务
     */
    public void update(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        try {
            //1.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //2.填充占位符
            for(int i = 0; i < args.length; i++){
                preparedStatement.setObject(i + 1, args[i]);
            }
            //3.执行SQL语句
//            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //4.关闭数据库资源，数据库连接 Connection 由调用者管理，此处不做关闭
            JdbcUtils.closeResource(null, preparedStatement, null);
        }
    }

    /**
     * 查询一条记录模板升级版，数据库连接 Connection 由调用者管理，以实现事务
     */
    public T queryOne(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T t = null;
        try {
            //1.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //3.执行SQL语句，并接收结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            //4.处理结果集
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
            //5.关闭数据库资源，数据库连接 Connection 由调用者管理，此处不做关闭
            JdbcUtils.closeResource(null, preparedStatement, resultSet);
        }
        return t;
    }

    /**
     * 查询多条记录模板升级版，数据库连接 Connection 由调用者管理，以实现事务
     */
    public List<T> queryList(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> result = new ArrayList<>();
        try {
            //1.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //3.执行SQL语句，并接收结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = metaData.getColumnCount();
            //4.处理结果集
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
            //5.关闭数据库资源，数据库连接 Connection 由调用者管理，此处不做关闭
            JdbcUtils.closeResource(null, preparedStatement, resultSet);
        }
        return result;
    }

    /**
     * 查询特殊值的通用模板，如某张表的总记录数、员工表中最大员工年龄等...
     * 数据库连接 Connection 由调用者管理，以实现事务
     */
    public <E> E getValue(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //3.执行SQL语句，并接收结果集
            resultSet = preparedStatement.executeQuery();
            //4.处理结果集
            if (resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭数据库资源，数据库连接 Connection 由调用者管理，此处不做关闭
            JdbcUtils.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

}
