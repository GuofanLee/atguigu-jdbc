package com.atgui.druid;

import com.atgui.druid.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 请填写类的描述
 *
 * @author GuofanLee
 * @date 2020-05-06 15:44
 */
public class DruidTest {

    @Test
    public void test1() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        System.out.println(connection);
    }

}
