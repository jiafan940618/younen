package com.yn.enums;

/**
 * Created by Xiang on 2017/7/24.
 */
public enum ApolegamyTypeEnum {

    // [类型]{0:优能的选配项目,1:服务商的选配项目}
    YN_APOLEGAMY(0, "优能的选配项目"),
    SERVER_APOLEGAMY(1, "服务商的选配项目"),
    ;

    private Integer code;
    private String message;

    ApolegamyTypeEnum(Integer code, String message) {
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
