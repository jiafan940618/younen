package com.yn.enums;

/**
 * Created by Xiang on 2017/7/24.
 */
public enum RoleEnum {

    ADMINISTRATOR(1L, "管理员"),
    AMMETER_ADMINISTRATOR(2L, "电表管理员"),
    BUSINESS_ADMINISTRATOR(3L, "业务管理员"),
    NOT_AUTHENTICATED_SERVER(4L, "未认证服务商"),
    AUTHENTICATED_SERVER(5L, "认证服务商"),
    ORDINARY_MEMBER(6L, "普通会员"),;

    private Long roleId;
    private String message;

    RoleEnum(Long roleId, String message) {
        this.roleId = roleId;
        this.message = message;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getMessage() {
        return message;
    }

}
