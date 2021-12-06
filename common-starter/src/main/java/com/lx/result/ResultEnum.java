package com.lx.result;

import java.util.Objects;

/**
 * @Describe
 * @Author yuj
 * @Date 2021/10/10
 */
public enum ResultEnum implements BaseEnum {

    SERVER_EXCEPTION(555,"服务器运行异常"),
    UN_KNOW_ERROR(500,"未知错误"),
    FAIL(-1,"操作失败"),
    SUCCESS(1,"操作成功"),
    REQUEST_PARAMS_ERROR(802, "参数为空或错误"),
    REQUEST_RESULT_ERROR(803,"返回结果为空"),
    RESOURCE_NOT_FOUND(804, "未找到该资源"),
    REQUEST_VALIDATION_FAILED(805, "请求数据格式验证失败"),
    DATA_BIND_EXCEPTION(901, "数据绑定异常"),
    DATA_INTEGRITY_EXCEPTION(902,"数据不完整"),
    SQL_EXCEPTION(903,"数据库异常"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.message = msg;
    }

    /**
     * 根据code值后去枚举对象
     * @param code
     * @return
     */
    public static ResultEnum getEnumByCode(Integer code) {
        for(ResultEnum e : ResultEnum.values()) {
            if(Objects.equals(e.getCode(), code)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
