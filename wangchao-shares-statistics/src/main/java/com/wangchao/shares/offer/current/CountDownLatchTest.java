 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer.current;

 import java.util.concurrent.CountDownLatch;

 /**
  * @author wangchao4
  * @date 2019/2/2815:06
  */
 public class CountDownLatchTest {


     /**
      * CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
      * 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了
      *
      *
      * CountDownLatch是一个同步辅助类，它允许一个或多个线程一直等待直到其他线程执行完毕才开始执行。
      * 用给定的计数初始化CountDownLatch，其含义是要被等待执行完的线程个数。
      *  每次调用CountDown()，计数减1
      *  主程序执行到await()函数会阻塞等待线程的执行，直到计数为0
      * @param args
      */
     public static void main(String[] args) {
         final CountDownLatch latch = new CountDownLatch(2);


         new Thread() {
             public void run() {
                 try {
                     System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                     Thread.sleep(3000);
                     System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                     latch.countDown();   //每次调用CountDown()，计数减1
                     System.out.println("线程个数" + latch.getCount());
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }

             ;
         }.start();


         new Thread() {
             public void run() {
                 try {
                     System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                     Thread.sleep(3000);
                     System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                     latch.countDown();
                     System.out.println("线程个数" + latch.getCount());
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }

             ;
         }.start();


         try {
             System.out.println("等待2个子线程执行完毕...");
             latch.await(); //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
             System.out.println("2个子线程已经执行完毕");
             System.out.println("继续执行主线程");
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

     }

 }



 class  thread1 implements Runnable{


     public void run() {

     }

 }
