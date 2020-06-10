package com.lian.protocol.common.myRedis;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/3 20:51
 */
public interface ValueOperations<K,V> {

    void set(K k,V v);

    V get(K k);
}
