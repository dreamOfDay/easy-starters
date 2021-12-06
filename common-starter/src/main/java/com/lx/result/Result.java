package com.lx.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 返回结果实体类
 **/
@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public Result() {}

    public Result(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(BaseEnum baseEnum){
        this.code = baseEnum.getCode();
        this.message = baseEnum.getMessage();
    }
}

