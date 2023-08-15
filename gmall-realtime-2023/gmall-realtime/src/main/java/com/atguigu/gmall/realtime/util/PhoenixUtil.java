package com.atguigu.gmall.realtime.util;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.realtime.common.GmallConfig;
import org.apache.commons.lang.StringUtils;
import org.mortbay.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;


public class PhoenixUtil {
    /**
     * connection: phoenix 连接
     * sinkTable:表名
     * data:数据
     *
     **/
    public static void upserValues(DruidPooledConnection connection, String sinkTable, JSONObject data) throws SQLException {
       //拼接sql语句：
        Set<String> columns = data.keySet();
        Collection<Object> values = data.values();
        String sql = "upsert into "  + GmallConfig.HBASE_SCHEMA + "." + sinkTable + "(" +
                StringUtils.join(columns,",") + ") values ('" +
                StringUtils.join(values,"','")+ "')";
        //2.预编译SQL
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //3.执行
        preparedStatement.execute();
        connection.commit();

        //4.释放资源
        preparedStatement.close();
    }
}
