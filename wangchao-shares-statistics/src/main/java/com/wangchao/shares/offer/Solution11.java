 /*
  * Copyright 2019 tuhu.cn All right reserved. This software is the
  * confidential and proprietary information of tuhu.cn ("Confidential
  * Information"). You shall not disclose such Confidential Information and shall
  * use it only in accordance with the terms of the license agreement you entered
  * into with Tuhu.cn
  */
 package com.wangchao.shares.offer;


 /**
  * 输入一个链表，输出该链表中倒数第k个结点。
  * 两个指针p1,p2，开始都指向头结点
  * 先让p2走k步
  * 然后p1,p2同时向下走
  * 当p2指向null的时候，p1就是倒数第k个节点
  *
  * @author wangchao4
  * @date 2019/2/1918:54
  */
 public class Solution11 {


     public ListNode FindKthToTail(ListNode head, int k) {

         if (head == null)
             return null;


         ListNode p1=head, p2=head;

         while (k>0&&p2!=null){
             p2 = p2.next;
             k--;
         }


         //p2 指向head说明k<=0,p2==null && k>0说明 k超过了链表的长度
         if(p2==head||(p2==null&&k>0))
             return null;

         while (p2!=null) {
             p1 = p1.next;
             p2 = p2.next;

         }
         return  p1;



     }


 }
