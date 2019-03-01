 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer.current;


 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;
 import java.util.concurrent.Semaphore;

 /*
     java信号灯
  * 创建一个缓存线程池，当向线程池丢入10个任务的时候，缓存线程池会生成10个线程去执行这10个任务，
  * 然后创建一个信号量对象，一次只允许3个线程同时访问资源，只有当三个线程中的某一个释放了信号灯，
  * 其他7个等待的线程才有机会执行，当然要看谁拿到该信号灯。
  */
 public class SempaphoreTest {


     public static void main(String[] args) {
         //创建一个缓存线程池
         ExecutorService pools = Executors.newCachedThreadPool();

         //创建具有给定的许可数和给定的公平设置的 Semaphore
         final Semaphore sp = new Semaphore(3, true);

         //向线程池中扔10个任务
         for (int i = 0; i < 10; i++) {

             Runnable runnable = new Runnable() {
                 @Override
                 public void run() {

                     try {
                         sp.acquire();  //获取
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }

                     System.out.println("线程" + Thread.currentThread().getName() + "进入，当前有" +
                             (3-sp.availablePermits()) + "个并发");

                     try {
                         Thread.sleep((long)(Math.random()*10000));
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }

                     System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
                     sp.release();//释放信号灯
                     System.out.println("线程" + Thread.currentThread().getName() + "已经离开，当前有" +
                             (3-sp.availablePermits()) + "个并发");

                 }
             };

             pools.execute(runnable);

         }


     }
 }
