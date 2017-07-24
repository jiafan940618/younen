package com.yn.enums;

import lombok.Getter;

/**
 * Created by Xiang on 2017/7/24.
 */
@Getter
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
}
