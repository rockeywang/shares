package com.wangchao.shares.offer;


/**
 * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
 * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
 */
public class Solution {

    /**
     * 后来发现StringBuffer的indexOf方法找不到时返回值为-1，那么直接使用-1
     * 来做判断代码简单很多。
     * @param str
     * @return
     */
    static public String replaceSpace(StringBuffer str) {

        int tar=str.indexOf(" ");
        while(tar!=-1){
            str.replace(tar,tar+1,"%20");
            tar=str.indexOf(" ",tar);
        }

        String result=str.toString();


        return result;

    }


    public static void main(String[] args) {
        StringBuffer stringBuilder=new StringBuffer();
        stringBuilder.append("we ");
        stringBuilder.append("Are ");
        System.out.println(replaceSpace(stringBuilder));
    }



}
