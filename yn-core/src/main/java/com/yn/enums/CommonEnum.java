package com.yn.enums;

/**
 * Created by Xiang on 2017/7/28.
 */
public enum CommonEnum {


    // [类型]{1:居民,2:工业,3:商业,4:农业}
    RESIDENT_TYPE(1, "居民"),
    INDUSTRY_TYPE(2, "工业"),
    BUSINESS_TYPE(3, "商业"),
    AGRICULTURE_TYPE(4, "农业"),



    ;

    private Integer code;
    private String message;

    CommonEnum(Integer code, String message) {
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
