package com.lian.protocol.common.globalexception.exception;

import com.lian.protocol.common.globalexception.constant.IResponseEnum;
import lombok.Getter;

/**
 * @author Ted
 * @date 2020/5/21 16:11
 */
@Getter
public class MyShiroException extends BaseException {
    private static final long serialVersionUID = 1L;

    public MyShiroException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public MyShiroException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
