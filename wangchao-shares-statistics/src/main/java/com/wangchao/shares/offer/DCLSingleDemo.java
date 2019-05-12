package com.wangchao.shares.offer;


/**
 * DCL模式的单列
 */
public class DCLSingleDemo {


      //volatile保证指令重排
     private static volatile DCLSingleDemo instance=null;


    /**
     * Double check lock机制不一定保证线程安全，原因是指令重排序存在
     * @return
     */
     public  static DCLSingleDemo getInstance(){
         if(instance==null){
            synchronized (DCLSingleDemo.class){
                if(instance==null){
                    instance=new DCLSingleDemo();
                }
             }
         }
         return instance;
     }

     public static void main(String [] args){

         for(int i=1;i<=10;i++) {
             new Thread(() -> {
                 DCLSingleDemo.getInstance();
             }, String.valueOf(i)).start();
         }
     }
}
