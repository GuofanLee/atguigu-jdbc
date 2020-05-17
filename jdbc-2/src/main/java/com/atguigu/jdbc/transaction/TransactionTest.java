package com.atguigu.jdbc.transaction;

import com.atguigu.jdbc.utils.JdbcUtils;
import com.atguigu.jdbc.utils.UpdateTemplate;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 使用事务和不使用事务功能测试
 *
 * @author GuofanLee
 * @date 2020-05-04 22:09
 */
public class TransactionTest {

    /**
     * user_table表：AA用户给BB用户转账100块钱
     * 未考虑事务，如果程序发生异常，不能回滚
     */
    @Test
    public void test1() {
        String sql1 = "update user_table set balance = balance - 100 where user = ?";
        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        //调用通用增、删、改操作模板
        UpdateTemplate.update(sql1, "AA");
//        int i = 10 / 0;       //模拟程序发生异常
        //调用通用增、删、改操作模板
        UpdateTemplate.update(sql2, "BB");
        System.out.println("转账成功");
    }

    /**
     * user_table表：AA用户给BB用户转账100块钱
     * 考虑事务，通过对数据库连接 Connection 对象的一系列操作完成事务管理
     */
    @Test
    public void test2() {
        Connection connection = null;
        try {
            //1.获取连接
            connection = JdbcUtils.getConnection();
            //2.关闭自动提交
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            //3.执行多条DML操作
            update(connection, sql1, "AA");
//            int i = 10 / 0;       //模拟程序发生异常
            update(connection, sql2, "BB");
            System.out.println("转账成功");
            //4.提交数据
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //5.回滚数据
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            /*
             * 6.恢复自动提交，此处恢不恢复无所谓，因为数据库连接 Connection 用完也就关了
             * 当使用数据库连接池时，必须恢复自动提交，以保证其它业务再使用当前数据库连接时能自动提交
             */
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //7.关闭数据库连接资源
            JdbcUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 通用增、删、改、操作模板升级版，数据库连接 Connection 由调用者管理，以实现事务
     */
    public static void update(Connection connection, String sql, Object... args) {
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
        } finally {
            //4.关闭数据库资源，数据库连接 Connection 由调用者管理，此处不做关闭
            JdbcUtils.closeResource(null, preparedStatement, null);
        }
    }

}
