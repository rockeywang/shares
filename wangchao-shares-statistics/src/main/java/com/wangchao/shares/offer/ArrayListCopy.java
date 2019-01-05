package com.wangchao.shares.offer;

import java.util.ArrayList;

/**
 * 输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。
 */
public class ArrayListCopy {

    /**
     * 解题思路：创建两个ArrayList，一个用来根据传进来的ListNode参数新建一个完整的ArrayList，另一个用来存储反转后的ArrayList。
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {

        ArrayList<Integer> arrayList = new ArrayList();
        ArrayList<Integer> result = new ArrayList();
        ListNode temp = listNode;

        while (temp != null) {
            arrayList.add(temp.val);
            temp = temp.next;
        }

        for (int i = arrayList.size() - 1; i >= 0; i--) {
            result.add(arrayList.get(i));
        }
        return result;
    }
}
