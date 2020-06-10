package com.lian.protocol.common.globalexception.exception.assertion;

import com.lian.protocol.common.globalexception.constant.IResponseEnum;
import com.lian.protocol.common.globalexception.exception.MyShiroException;

import java.text.MessageFormat;


/**
 * @author Ted
 * @date 2020/5/21 16:13
 */
public interface ShiroExceptionAssert extends IResponseEnum, Assert {

    @Override
    default MyShiroException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new MyShiroException(this, args, msg);
    }

    @Override
    default MyShiroException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new MyShiroException(this, args, msg, t);
    }
}
