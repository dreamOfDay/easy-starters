package com.lx.components;

import java.util.Date;

import com.lx.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @Author: yuj
 * @Date: 2021/12/9
 * @Describe:
 */
public class SimpleDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        return DateUtils.parse(source);
    }

}
