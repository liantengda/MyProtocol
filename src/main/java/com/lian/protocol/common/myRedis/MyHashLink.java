package com.lian.protocol.common.myRedis;

import lombok.Data;

/**
 * @author Ted
 * @version 1.0
 * @date 2020/5/23 13:36
 */
@Data
public class MyHashLink<K,V> {
    /**
     * 自定义尾部节点，一直指向链表尾部
     */
    private MyEntry last = null;
    /**
     * 自定义头节点，一直指向链表头部
     */
    private MyEntry first = null;
    /**
     * 自定义遍历节点，每次遍历完成记得都置为空
     */
    private MyEntry iteratorNode = null;
    /**
     * 链表元素的数量
     */
    private int realSize = 0;

    public MyHashLink(){

    }

    private static class MyEntry<K,V>{
        V value;
        K key;
        MyEntry<K,V> next;
        MyEntry<K,V> prev;
        MyEntry(MyEntry<K,V> prev, K key,V value,MyEntry<K,V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    public V getHashNode(K key){
        iteratorNode = first;
        boolean findResult = false;
        V result = null;
        while (!findResult){
            if(iteratorNode.key.equals(key)){
                result = (V)iteratorNode.value;
                findResult = true;
            }else {
                if(iteratorNode.next!=null){
                    iteratorNode = iteratorNode.next;
                    if(iteratorNode.key.equals(key)){
                        result = (V)iteratorNode.value;
                        findResult = true;
                    }
                }else {
                    findResult = true;
                }
            }
        }
        return   result;
    }

    public V add(K key,V value){
        MyEntry<K,V> newNode = new MyEntry<K,V>(last,key,value,null);
        if(first == null){
            first = newNode;
            last = first;
        }else{
            last.next = newNode;
            last = newNode;
        }
        realSize++;
        return value;
    }

    public int size(){
        return realSize;
    }


    public Object get(int index){
        MyEntry requireNode = pinPoint(index);
        return requireNode.value;
    }

    /**
     * 根据index精准定位一个节点
     * @param index
     * @return
     */
    public MyEntry pinPoint(int index){
        iteratorNode = first;
        for (int i = 0;i<index;i++){
            iteratorNode = iteratorNode.next;
        }
        MyEntry requireNode = iteratorNode;
        iteratorNode = null;
        return requireNode;
    }


    public static void main(String[] args) {
//        MyHashLink<Integer> integers = new MyHashLink<>();
//        integers.add(1);
//        integers.add(9);
//        integers.add(10);
//        Object o = integers.get(2);
//        System.out.println(o);
//        System.out.println(integers.size());
//        Object o1 = integers.addByIndex(1, 520);
//        System.out.println(o1);
    }
}
