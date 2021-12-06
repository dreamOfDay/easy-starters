package com.lx.result;

/**
 * @Describe 返回结果基础枚举
 * @Author yuj
 * @Date 2021/10/10
 */
public interface BaseEnum {

    /**
     * 获取异常编码
     * @return int
     */
    Integer getCode();

    /**
     * 获取异常信息
     * @return string
     */
    String getMessage();
}
