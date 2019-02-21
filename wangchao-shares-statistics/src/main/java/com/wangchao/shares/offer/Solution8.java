 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;

 /**
  * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
  *
  * @author wangchao4
  * @date 2019/2/1915:55
  */
 public class Solution8 {


     public static int NumberOf1(int n) {

         int index = 1;

         int number = 0;

         while (index != 0) {
             if ((n & index) != 0) {
                 number++;
             }

             index = index << 1;
         }

         return number;

     }

     public static void main(String[] args) {
         NumberOf1(23233112);
     }

 }
