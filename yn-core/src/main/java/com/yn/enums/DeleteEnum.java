package com.yn.enums;

/**
 * Created by Xiang on 2017/7/27.
 */
public enum DeleteEnum {

    DEL(1, "已经删除"),
    NOT_DEL(0, "未删除"),
    ;

    private Integer code;
    private String message;

    DeleteEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
