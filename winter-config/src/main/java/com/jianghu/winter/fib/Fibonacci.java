package com.jianghu.winter.fib;

/**
 * 斐波那契数列
 * like: new Integer[]{0,1,1,2,3,5,8,13,21,34}
 *
 * @Author: daniel.hu
 * @Date: 2021/5/14 13:32
 */
public class Fibonacci {
    public static boolean isLegal(Integer[] array) {
        if (array.length < 2) {
            return false;
        } else {
            int first = array[0];
            int second = array[1];
            if (compare(second, first)) {
                for (int i = 2; i < array.length; i++) {
                    int sum = first + second;
                    if (sum != array[i]) {
                        return false;
                    }
                    first = second;
                    second = sum;
                }
                return true;
            }
        }
        return false;
    }

    private static boolean compare(int second, int first) {
        while (true) {
            int diff = second - first;
            if (diff == 0 && first == 1) {
                return true;
            }
            if (diff < 0) {
                return false;
            }
            second = first;
            first = diff;
        }
    }
}
