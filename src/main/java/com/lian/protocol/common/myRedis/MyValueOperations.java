package com.lian.protocol.common.myRedis;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/6/3 20:50
 */
public class MyValueOperations<K,V> implements ValueOperations<K,V> {

    private  Dict<K,V> myRedisDB = new Dict<>();

    @Override
    public void set(K k, V v) {
        myRedisDB.add(k,v);
    }

    @Override
    public V get(K k) {
        V v = myRedisDB.get(k);
        return v;
    }
}
