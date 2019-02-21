 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;

 /**
  * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
  *
  * @author wangchao4
  * @date 2019/2/1911:04
  */
 public class Solution7 {


     /**
      * 类似于青蛙跳台阶,当n=1时，只有一种横向排列的方式。当n等于二时，2*2有两种选择，横向或者是竖向。当n等于3的时候对于2*3来说,如果选择的是竖向排列，则剩下的就是2*2排列，
      * 如果选择的是横向,则对于2*n剩下的则只有1*n的一种选择。所以依次类推，找到迭代RectCover(target-1)+RectCover(target-2)。
      * @param target
      * @return
      */
     public int RectCover(int target) {

         if(target<=0){
             return 0;
         }

         else if(target==1||target==2){
             return target;
         }

         return (RectCover(target-1)+RectCover(target-2));






     }


 }
