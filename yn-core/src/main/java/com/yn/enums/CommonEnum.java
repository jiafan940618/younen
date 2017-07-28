package com.yn.enums;

/**
 * Created by Xiang on 2017/7/28.
 */
public enum CommonEnum {


    // [类型]{0:居民,1:工业,2:商业,3:农业}
    RESIDENT_TYPE(0, "居民"),
    INDUSTRY_TYPE(1, "工业"),
    BUSINESS_TYPE(2, "商业"),
    AGRICULTURE_TYPE(3, "农业"),



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
