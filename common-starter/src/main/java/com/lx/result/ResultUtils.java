package com.lx.result;

/**
 * @Describe 返回结果工具类，可用此工具类构造统一格式并返回
 * @Author yuj
 * @Date 2021/10/10
 */
public class ResultUtils {

    public static Result success(Object object){
        Result result = new Result(ResultEnum.SUCCESS);
        result.setData(object);
        return result;
    }

    public static Result success(){
        return success(null);
    }


    public static Result info(BaseEnum baseEnum, Object data){
        return error(baseEnum, data);
    }

    public static Result info(Integer code, String message, Object data){
        return error(code, message, data);
    }

    public static Result error(BaseEnum baseEnum){
        return error(baseEnum, null);
    }

    public static Result error(Integer code, String message){
        return error(code, message, null);
    }

    public static Result error(BaseEnum baseEnum, Object data){
        return error(baseEnum.getCode(), baseEnum.getMessage(), data);
    }

    public static Result error(Integer code, String message, Object data){
        Result result = new Result(code, message);
        result.setData(data);
        return result;
    }
}
