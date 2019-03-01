 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer.current;

 import java.io.File;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.concurrent.Phaser;

 /**
  * @author wangchao4
  * @date 2019/2/2816:52
  */
 public class PhaserTest {


     /**
      *
      * @param args
      */
     public static void main(String[] args) {

         Phaser phaser = new Phaser(3);
         FileSearch system = new FileSearch("E:\\a", ".txt",
                 phaser);
         FileSearch apps = new FileSearch("E:\\b", ".txt",
                 phaser);
         FileSearch documents = new FileSearch("E:\\c", ".txt",
                 phaser);

         Thread systemThread=new Thread(system, "system-a");
         systemThread.start();

         Thread appsThread=new Thread(apps, "apps-b");
         appsThread.start();


         Thread documentsThread=new Thread(documents, "documents-c");
         documentsThread.start();


         try {
             systemThread.join();
             appsThread.join();
             documentsThread.join();
         } catch (Exception e) {
             e.printStackTrace();
         }
         System.out.println("Terminated:"+ phaser.isTerminated());


     }


 }

 class FileSearch implements Runnable {

     private String initPath;// 查找路径
     private String end;// 文件后缀
     private List<String> results;// 结果集
     private Phaser phaser;

     public FileSearch(String initPath, String end, Phaser phaser) {
         this.initPath = initPath;
         this.end = end;
         this.phaser = phaser;
         this.results = new ArrayList<String>();
     }

     @Override
     public void run() {
         phaser.arriveAndAwaitAdvance();
         System.out.println(Thread.currentThread().
                 getName()+": Starting");
         File file=new File(initPath);
//         if(file.isDirectory()){
//             direactoryProcess(file);
//         }
//         if(!checkResults()){
//             return;
//         }
//         filterResult();
//         if(!checkResults()){
//             return;
//         }
//         showInfo();
         phaser.arriveAndDeregister();
         System.out.println(Thread.currentThread().
                 getName()+": Work completed");
     }
 }
