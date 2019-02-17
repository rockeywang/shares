package com.wangchao.shares.offer;


/**
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
 *
 * f(1) = 1
 *
 * f(2) 会有两个跳得方式，一次1阶或者2阶，这回归到了问题f(1),f(2) = f(2-1) + f(2-2)
 *
 * f(3) 会有三种跳得方式，1阶、2阶、3阶，那么就是第一次跳出1阶后面剩下：f(3-1);第一次跳出2阶，剩下f(3-2)；第一次3阶，那么剩下f(3-3).因此结论是
 * f(3) = f(3-1)+f(3-2)+f(3-3)
 *
 * f(n)时，会有n中跳的方式，1阶、2阶...n阶，得出结论：
 *
 * f(n) = f(n-1)+f(n-2)+...+f(n-(n-1)) + f(n-n) => f(0) + f(1) + f(2) + f(3) + ... + f(n-1) == f(n) = 2*f(n-1)
 */
public class Solution6 {

    public int JumpFloorII(int target) {
            if(target==1){
                return 1;
            }

            return 2*JumpFloorII(target-1);
    }



}
