package com.lx.mapper.util;

import com.lx.components.SpringContextUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.crazycake.jdbcTemplateTool.JdbcTemplateTool;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jyu
 * @Date: 2021/9/14
 * @Description: this class is used to integration {@link JdbcTemplate} and {@link NamedParameterJdbcTemplate}
 **/
public class JdbcTemplateUtils {

    private static JdbcTemplate jt;
    private static NamedParameterJdbcTemplate njt;
    private static JdbcTemplateTool jtt;

    private static JdbcTemplate getJt() {
        if (jt == null) {
            jt = SpringContextUtils.getBean(JdbcTemplate.class);
        }

        return jt;
    }

    private static NamedParameterJdbcTemplate getNJt() {
        if (njt == null) {
            njt = SpringContextUtils.getBean(NamedParameterJdbcTemplate.class);
        }
        return njt;
    }

    public static JdbcTemplateTool getJtt() {
        if (jtt == null) {
            jtt = new JdbcTemplateTool();
            jtt.setJdbcTemplate(getJt());
        }

        return jtt;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return getJt();
    }

    public static NamedParameterJdbcTemplate getNamedJdbcTemplate() {
        return getNJt();
    }

    public static DataSource getDataSource() {
        return getJt().getDataSource();
    }

    public static Connection getConnection() {
        try {
            return getJt().getDataSource().getConnection();
        } catch (SQLException var1) {
            throw new RuntimeException(var1);
        }
    }

    public static List query(String sql) {
        return getJdbcTemplate().queryForList(sql);
    }

    public static List<ListOrderedMap> query2(String sql) {
        List list = getJt().query(sql, new ColumnMapRowMapper() {
            protected Map createColumnMap(int columnCount) {
                return new ListOrderedMap();
            }
        });
        return list;
    }

    public static int update(String sql) {
        return getJt().update(sql);
    }

    public static void execute(String sql) {
        getJt().execute(sql);
    }

    public static int[] batchUpdate(String[] sql) {
        return getJt().batchUpdate(sql);
    }

    public static int[] batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter) {
        return getJt().batchUpdate(sql, batchPreparedStatementSetter);
    }

    public static LinkedHashMap<String, Object> getMapData(String sql) {
        final LinkedHashMap<String, Object> datas = new LinkedHashMap();
        getJt().query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                datas.put(rs.getString(1), rs.getObject(2));
            }
        });
        return datas;
    }

    public static Map<String, Object> getMapDataByOrder(String sql) {
        final Map<String, Object> datas = new LinkedHashMap();
        getJt().query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                datas.put(rs.getString(1), rs.getObject(2));
            }
        });
        return datas;
    }

}

