 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;

 import java.util.Arrays;

 /**
  * @author wangchao4
  * @date 2019/3/1115:48
  */
 public class ArrayUnique {


     /**
      * 数组去重
      *该算法利用了差分来剔除重复值。首先对数组进行排序，初始化长度为数组长度的boolean数组diff来保存数据的差分信息，
      * 如果差分等于0，说明该值重复，diff数组在此记作false，不等于零则记作true。最后遍历diff数组，将为true值下标的值添加到
      * unique数组。再把第一个数添加其中(第一个数肯定与前面不重复)，最后为了让结果好看，排序unique数组即可。
      * @param array
      * @return
      */
     public static int[] unique(int[] array) {

         int len = -1;

         if (array == null || (len = array.length) < 2) {

             return len == -1 ? null : Arrays.copyOf(array, len);

         }

         array = Arrays.copyOf(array, len);

         Arrays.sort(array);

         boolean[] diffs = new boolean[len];

         diffs[0] = true;

         int uCount=1;

         for(int i=1;i<len;i++){
             if(array[i]!=array[i-1]){
                 uCount++;
                 diffs[i] = true;
             }
         }

         int [] uniqueArray=new int[uCount];

         for(int i=0, index=0;i<len;i++){
             if(diffs[i]){
                 uniqueArray[index++]=array[i];
             }
         }

         return uniqueArray;

     }
 }
