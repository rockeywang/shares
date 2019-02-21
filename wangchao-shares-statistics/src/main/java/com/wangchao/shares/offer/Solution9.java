 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;

 /**
  * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
  *
  * @author wangchao4
  * @date 2019/2/1916:13
  */
 public class Solution9 {


     public static double Power(double base, int exponent) {

         double mul = 1.0;

         /* 如果exponent = 0 输出1 */
         if (exponent == 0) {
             return 1.00000;
         }


         if (base >= -0.000001 && base <= 0.000001) {
             return 0;
         }

         if(exponent<0){
             for (int index = 0; index < -exponent; index++) {
                  mul *= base;
         }
             mul = 1.0 / mul;
         }
         else{
             for (int index = 0; index < exponent; index++) {
                 mul *= base;
             }
         }



         return mul;
     }


     public static void main(String[] args) {
         System.out.println(Power(2, -3));
     }
 }
