package com.lian.protocol.common.enums;

import com.lian.protocol.common.globalexception.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ted
 * @date 2020/6/3 14:21
 */
@Getter
@AllArgsConstructor
public enum MyBusinessExceptionEnum implements BusinessExceptionAssert {

    USER_ALREADY_EXIST(1001,"用户已经存在"),
    USER_PASSWORD_NOT_NULL(1002,"用户密码不能为空"),
    USER_PASSWORD_WRONG(1005,"用户密码错误"),
    USER_PHONE_NOT_NULL(1003,"用户电话不能为空"),
    USER_NOT_NULL(1004,"用户还未注册"),
    USER_ALREADY_DELETED(1007,"用户已经被删除"),
    USER_NOT_EXIST(1006,"用户不存在");

    private int code;

    private String message;

}
