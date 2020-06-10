package com.lian.protocol.common.myRedis;

import org.springframework.stereotype.Component;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/3 20:45
 */
@Component
public class MyRedisTemplate {

    private static MyValueOperations myValueOperations = new MyValueOperations();

    public MyValueOperations opsForValue(){
        return myValueOperations;
    }
}
