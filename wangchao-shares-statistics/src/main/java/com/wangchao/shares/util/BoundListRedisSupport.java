package com.wangchao.shares.util;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;


public class BoundListRedisSupport<T> {

    @SuppressWarnings("unchecked")
    private Class<T> tClass = ReflectionUtils.getSuperClassGenricType(this.getClass());

    protected RedisTemplate<Serializable, T> redisTemplate;

    protected BoundListOperations<Serializable, T> boundListOperations;


    protected String key = tClass.getSimpleName() + ":list";

    public RedisTemplate<Serializable, T> getRedisTemplate() {
        return redisTemplate;
    }

    protected BoundListRedisSupport() {

    }

    protected BoundListRedisSupport(String key) {
        this.key = key;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        boundListOperations = this.redisTemplate.boundListOps(key);
    }


    public Long push(T value){
        return this.boundListOperations.leftPush(value);
    }


    public T pop() {
        return this.boundListOperations.leftPop();
    }



    public T blockPop() {

        return redisTemplate.execute(new RedisCallback<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T doInRedis(RedisConnection connection) {
                List<byte[]> results = connection.bLPop(0, redisTemplate
                        .getStringSerializer().serialize(key));
                if (results == null || results.isEmpty()) {
                    return null;
                }
                return (T) redisTemplate.getValueSerializer().deserialize(
                        results.get(1));
            }

        });

    }




    public T blockPop(final int timeOut) {

        return redisTemplate.execute(new RedisCallback<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T doInRedis(RedisConnection connection) {
                List<byte[]> results = connection.bLPop(timeOut, redisTemplate.getStringSerializer().serialize(key));
                if(results == null || results.isEmpty()){
                    return null;
                }
                return (T)redisTemplate.getValueSerializer().deserialize(results.get(1));
            }

        });

    }


    public Long in(T value) {
        return this.boundListOperations.rightPush(value);
    }
    public Long inAll(T... values) {
        return this.boundListOperations.rightPushAll(values);
    }


    public Long length() {
        return this.boundListOperations.size();
    }


    public List<T> range(int start, int end) {
        return this.boundListOperations.range(start, end);
    }


    public void remove(long count, T value) {
        this.boundListOperations.remove(count, value);
    }


    public T index(long index) {
        return this.boundListOperations.index(index);
    }


    public void set(long index, T value) {
        this.boundListOperations.set(index, value);
    }


    public void trim(long start, int end) {
        this.boundListOperations.trim(start, end);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
