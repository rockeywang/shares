package com.wangchao.shares.offer;



/**
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class FindArray {


    public static void main(String[] args) {
        int[][] arr = { {1,2,8,9}, {2,4,9,12},{4,7,10,13},{6,8,11,15}};
        System.out.println(Find(6,arr));
    }


    /*思路：选取右上角元素，
    元素大于Key--》剔除整列，
    元素小于key--》剔除整行*/

    /*注意点就是循环的条件
     * 对于row行 从上0开始递增，停止的条件是小于行的长度
     * 对于column列，从右边列的长度开始递减，停止的条件是大于等于零
     * （因为查询最右下角的元素时，第0列是允许的不能停止。
     * */

    static public boolean Find(int target, int [][] array) {
        int row_len = array.length;
        int column_len = array[0].length;//列的长度4
        int row;
        int column;//3
        for (row = 0, column = column_len - 1; column >= 0 && row < row_len; ) {
            if (target == array[row][column]) {
                return true;
            }
            if (array[row][column] > target) {
                column--;
                continue;
            }
            if (array[row][column] < target) {
                row++;
                continue;
            }

        }
        return false;
    }

}
