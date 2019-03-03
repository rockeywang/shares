package com.wangchao.shares.offer;


import java.util.concurrent.locks.ReentrantLock;

/**
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
 * n<=39
 */
public class Solution4 {

    /**
     * 解题思路: 0,n=0
     *           1,n=1
     *           f(n)=f(n-1)+f(n-2),n>1
     * @param n
     * @return
     */
    public int Fibonacci(int n) {
        int[] num = new int[100];
        num[0] = 0;
        num[1] = 1;
        for (int i = 2; i < 100; i++) {
            num[i] = num[i - 1] + num[i - 2];
        }

        return num[n];
    }
}
