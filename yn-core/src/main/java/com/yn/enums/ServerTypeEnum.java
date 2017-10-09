package com.yn.enums;

/**
 * Created by Xiang on 2017/7/24.
 */
public enum ServerTypeEnum {

    NOT_AUTHENTICATED(0, "未认证的服务商"),
    AUTHENTICATED(1, "已经认证的服务商"),
    ;

    private Integer code;
    private String message;

    ServerTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
