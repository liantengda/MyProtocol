package com.lian.protocol.common.myRedis;

import lombok.extern.slf4j.Slf4j;

/**
 * 哈希表
 * @author Ted
 * @date 2020/5/27 15:27
 */
@Slf4j
public class Dict<K,V> {

    private static  int defaultInitialCapacity = 16;
    private MyHashLink<K,V>[] array = new MyHashLink[defaultInitialCapacity];


    public void add(K key, V value){
        int hash = hash(key);
        int index = (defaultInitialCapacity-1)&hash;
        if(array[index]==null){
            MyHashLink<K,V> newMyLink = new MyHashLink<>();
            newMyLink.add(key,value);
            array[index] = newMyLink;
        }else{
            array[index].add(key,value);
        }
    }

    public V get(K key) {
        int hash = hash(key);
        int index = (defaultInitialCapacity-1)&hash;
         V result;
        if(array[index]==null){
            return null;
        } else {
            result = array[index].getHashNode(key);
        }
        return result;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        Dict<String, Object> myDict = new Dict<>();
        myDict.add("ted","连腾达");
        myDict.add("呵呵","哈哈");
        long initialHashStartTime = System.currentTimeMillis();
        for (int i = 0;i<1000000;i++){
            myDict.add(String.valueOf(i),String.valueOf(i));
        }
        long initialHashEndTime = System.currentTimeMillis();
        long queryStartTime = System.currentTimeMillis();
        System.out.println(myDict.get("8888888"));
        long queryEndTime = System.currentTimeMillis();
        log.info("initialTime{},queryTime{}",(initialHashEndTime-initialHashStartTime),(queryEndTime-queryStartTime));
    }

    /**
     * 十进制转换为二进制
     * @param decimal
     * @return
     */
    public static String decimalToBinary(int decimal){
        String myBinary = "";
        while (decimal>2){
            int i = 0;
            i = decimal%2;
            decimal = decimal/2;
            myBinary += i;
        }
        return myBinary;
    }


    /**
     * 反转数组
     * @param s
     * @return
     */
    public static String invert(String s){
        char[] chars = s.toCharArray();
        for (int i = 0;i<chars.length/2;i++){
            char temp  = 0;
            temp = chars[i];
            chars[i] = chars[chars.length-1-i];
            chars[chars.length-1-i] = temp;
        }
        String invertResult = new String(chars);
        return invertResult;
    }

}
