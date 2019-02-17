package com.wangchao.shares.offer;


import java.util.Stack;

/**
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 */
public class Solution2 {


    /**
     * 主要思路是，一个stack1占用来接收正常的压栈，一个stack2用来存放stack1的倒序，
     * 当执行pop()操作时，弹出的是stack2的栈顶元素，就会有“先进先出的效果”。
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
          //stack1.push(node);
        while(!stack2.isEmpty()){
            stack1.push(stack2.pop());
        }
        stack1.push(node);
        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }
    }

    public int pop() {
        return   stack2.pop();
    }


}
