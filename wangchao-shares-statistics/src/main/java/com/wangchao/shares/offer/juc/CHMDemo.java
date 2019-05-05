 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer.juc;

 import java.util.concurrent.ConcurrentHashMap;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.atomic.AtomicInteger;

 /**
  * @author wangchao4
  * @date 2019/4/1916:13
  */
 public class CHMDemo {


     /**
      * 这段使用ConcurrentHashMap的代码，产生了线程安全的问题。我们来分析一下为什么会发生这种情况。在step1跟step2中，都只是调用ConcurrentHashMap的方法，各自都是原子操作，是线程安全的。
      * 但是他们组合在一起的时候就会有问题了，A线程在进入方法后，通过map.get("key")拿到key的值，刚把这个值读取出来还没有加1的时候，线程B也进来了，那么这导致线程A和线程B拿到的key是一样的。不仅仅是在
      * ConcurrentHashMap，在其他的线程安全的容器比如Vector之类的也会出现如此情况，所以在使用这些容器的时候还是不能大意。
      *
      * 用原子类可以解决
      */
     public static void main(String[] args) throws InterruptedException {
//         ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
//         map.put("key", 1);
//         ExecutorService executorService = Executors.newFixedThreadPool(100);
//         for (int i = 0; i < 1000; i++) {
//             executorService.execute(new Runnable() {
//                 @Override
//                 public void run() {
//                     int key = map.get("key") + 1; //step 1
//                     map.put("key", key);//step 2
//                 }
//             });
//         }
//         Thread.sleep(3000); //模拟等待执行结束
//         System.out.println("------" + map.get("key") + "------");
//         executorService.shutdown();



         ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<String,AtomicInteger>();
         AtomicInteger integer = new AtomicInteger(1);
         map.put("key", integer);
         ExecutorService executorService = Executors.newFixedThreadPool(100);
         for (int i = 0; i < 1000; i++) {
             executorService.execute(new Runnable() {
                 @Override
                 public void run() {
                     map.get("key").incrementAndGet();
                 }
             });
         }
         Thread.sleep(3000); //模拟等待执行结束
         System.out.println("------" + map.get("key") + "------");
         executorService.shutdown();

     }

 }
