package com.lx.mapper;

import com.lx.mapper.provider.InsertOnKeyUpdateProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 示例如何拓展mapper，可以参照此方式拓展
 **/
@RegisterMapper
public interface BaseInsertListMapper<T> extends BaseMapper<T>, InsertListMapper<T> {

    @InsertProvider(type = InsertOnKeyUpdateProvider.class, method = "dynamicSQL")
    int insertOnKeyUpdate(T record);

}
