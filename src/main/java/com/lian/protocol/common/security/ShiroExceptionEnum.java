package com.lian.protocol.common.security;

import com.lian.protocol.common.globalexception.exception.assertion.ShiroExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ted
 * @date 2020/5/21 16:32
 */
@Getter
@AllArgsConstructor
public enum ShiroExceptionEnum implements ShiroExceptionAssert {
    TOKEN_IS_INVALID(1,"token无效");
    private int code;
    private String message;
}
