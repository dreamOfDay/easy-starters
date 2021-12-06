package com.lx.mapper.provider;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 示例拓展sql
 **/
public class InsertOnKeyUpdateProvider extends MapperTemplate {
    private final String ON_KEY = " ON DUPLICATE KEY UPDATE ";

    //
    public InsertOnKeyUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insertOnKeyUpdate(MappedStatement ms) {
        //获取对应实体
        final Class<?> entityClass = getEntityClass(ms);

        StringBuilder sqlBuilder = new StringBuilder();

        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        String tableName = SqlHelper.getDynamicTableName(entityClass, tableName(entityClass));


        String insertSQL = new SQL() {{
            //构建语句
            INSERT_INTO(tableName);
            for(EntityColumn column : columnList){
                VALUES(column.getColumn(),column.getColumnHolder());
            }
        }}.toString();
        //
        sqlBuilder.append(insertSQL).append(ON_KEY);
        String updateSql = ifAllColumn(columnList);
        sqlBuilder.append(updateSql);
        //现在是这样的形式 INSERT INTO collection_package(id,`name`,priority,mark,created_by,updated_by) VALUES (3,"AAA",1,"#C",1,1) ON DUPLICATE KEY UPDATE `name` = "AAA";
        return sqlBuilder.toString();
    }

    private String ifAllColumn(Set<EntityColumn> columnList){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("<trim suffixOverrides=\",\">");
        for(EntityColumn column : columnList) {
            String property = column.getProperty();
            sqlBuilder.append("<if test=\""+property+"!=null\">");
            sqlBuilder.append(" "+column.getColumnEqualsHolder()+", ");
            sqlBuilder.append("</if>");
        }
        sqlBuilder.append("</trim>");
        return sqlBuilder.toString();
    }
}
