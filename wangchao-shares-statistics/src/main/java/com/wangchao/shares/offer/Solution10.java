 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;

 /**
  * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
  * 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
  *
  * @author wangchao4
  * @date 2019/2/1916:49
  */
 public class Solution10 {


     /**
      * 如果不考虑奇数和奇数，偶数和偶数之间的相对位置不变。那么只需要两个指针分别指向数组的头和尾，依次比较。
      * 1. 如果头指针指向的数组位置为奇数，那么就判断尾指针指向的数组位置的奇偶性。如果是奇数，则头指针后移一个位置，如果是偶数，则尾指针前移一个位置。
      * 2. 如果头指针指向的数组位置为偶数，那么就判断尾指针指向的数组位置的奇偶性。如果是奇数，则交换头尾指针指向的数组元素，如果是偶数，则尾指针前移一个位置。
      *
      * @param array
      */

     public static void reOrderArray(int[] array) {


       for(int i=0;i<array.length-1;i++){
           for(int j=0;j<array.length-1;j++){
               if(array[j]%2==0&&array[j+1]%2==1){
                   int t = array[j];
                   array[j]=array[j+1];
                   array[j+1]=t;
               }
           }
       }
     }



     public static void main(String [] args){
         int []array = {1,2,3,4,5,6,7,8,9,10,11,12,13};
         reOrderArray(array);
         for(int i=0;i<array.length;i++)
         {
             System.out.print(array[i]+" ");
         }
     }
 }
