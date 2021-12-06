package com.lx.exception;

import com.lx.result.BaseEnum;
import lombok.Data;

/**
 * @Author： yuj
 * @Date： 2021/10/12
 * @Describe：基础异常类
 */
@Data
public class BaseException extends RuntimeException implements BaseEnum{
    private Integer code;
    private Object data;

    public BaseException(BaseEnum baseEnum){
        this(baseEnum.getCode(), baseEnum.getMessage(), null);
    }

    public BaseException(BaseEnum baseEnum, Object data){
        this(baseEnum.getCode(), baseEnum.getMessage(), data);
    }

    public BaseException(Integer code, String message){
        this(code, message, null);
    }

    public BaseException(Integer code, String message, Object data){
        super(message);
        this.code = code;
        this.data = data;
    }

    public BaseException(String message) {
        super(message);
    }
}
