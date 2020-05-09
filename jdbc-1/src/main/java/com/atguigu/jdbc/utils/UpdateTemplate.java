package com.atguigu.jdbc.utils;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 通用增、删、改、操作模板
 *
 * @author GuofanLee
 * @date 2020-05-03 10:59
 */
public class UpdateTemplate {

    /**
     * 向 customers 表中添加一条记录
     */
    @Test
    public void update() throws ParseException {
        String sql = "insert into customers(name, email, birth) values (?, ?, ?)";
        String name = "蔡徐坤";
        String email = "cxk@sb.com";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date birth = sdf.parse("2020-02-02");
        update(sql, name, email, new java.sql.Date(birth.getTime()));
    }

    /**
     * 通用增、删、改、操作模板，每次调用都是一个单独的事务
     *
     * <p>每次调用都是创建新的数据库连接，而新创建的数据库连接默认每条SQL执行成功自动提交数据</p>
     */
    public static void update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取数据库连接
            connection = JdbcUtils.getConnection();
            //2.预编译SQL语句，获取PreparedStatement实例
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for(int i = 0; i < args.length; i++){
                preparedStatement.setObject(i + 1, args[i]);
            }
            //4.执行SQL语句
//            int affectedRows = preparedStatement.executeUpdate();
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //5.关闭数据库资源
            JdbcUtils.closeResource(connection, preparedStatement, null);
        }
    }

}
