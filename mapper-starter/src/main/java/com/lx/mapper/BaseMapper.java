package com.lx.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 基础通用mapper，使用tk.mybatis的mapper可以直接继承此类使用
 **/
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
