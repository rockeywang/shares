 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;

 /**
  * 输入一个链表，反转链表后，输出新链表的表头。
  *
  * @author wangchao4
  * @date 2019/2/2015:21
  */
 public class Solution12 {


     public ListNode ReverseList(ListNode head) {


         if(head==null){
             return null;
         }

         //最终需要实现的是 将列表1 -> 2 -> 3 -> 4 转换为1 <- 2 <-3 <- 4
         //为了能够快速反转列表，需要定义节点tem来保存当前节点的next值,定义pre来保存节点的上一个值

         ListNode tem=null;
         ListNode pre=null;

         while(head!=null){
             //保存当前节点的下一个节点，防止出现断列
             tem=head.next;
             //保存好之后，则将当前节点指向pre
             //例如，当前是第一次循环，则1 -> 2 转变为  null <- 1;方向发生改变，代码实现如

             head.next=pre;

             pre=head;

             head=tem;

         }

         return pre;

     }
 }
