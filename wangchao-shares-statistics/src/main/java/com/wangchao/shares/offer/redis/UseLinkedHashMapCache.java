package com.wangchao.shares.offer.redis;

import org.apache.catalina.User;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * redis缓存淘汰机制  LRU算法
 * @param <k>
 * @param <v>
 */
public class UseLinkedHashMapCache<k, v> extends LinkedHashMap<k, v> {



    private int cacheSize;

    private final Lock lock = new ReentrantLock();


    /**
     *  initialCapacity
     * @param cacheSize
     */
    public  UseLinkedHashMapCache(int cacheSize){
        super(16,0.75f,true);
        this.cacheSize=cacheSize;
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<k, v> eldest) {
        return size()>cacheSize;
    }



    @Override
    public v get(Object key) {

        try {
            lock.lock();
            return super.get(key);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public v put(k key, v value) {
        try {
            lock.lock();
            return super.put(key,value);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        UseLinkedHashMapCache<Integer, String> cache = new UseLinkedHashMapCache<Integer, String>(4);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");
        cache.put(4, "four");
        cache.put(5, "five");
        cache.put(6, "six");

        Iterator<Map.Entry<Integer, String>> it = cache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            Integer key = entry.getKey();
            String Value = entry.getValue();
            System.out.println("key:\t" + key + "value:\t" + Value);


        }
    }
}
