package com.jianghu.winter.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 *
 * @Author: daniel.hu
 * @Date: 2021/7/13 19:06
 */
public class BubbleSort {

    public static int[] sort(int[] input) {
        int l = input.length;
        if (l == 1) {
            return input;
        }
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l - i - 1; j++) {
                if (input[j] > input[j + 1]) {
                    int temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
        }
        return input;
    }

    public static void main(String[] args) {
        int[] sort = sort(new int[]{2, 5, 9, 4, 6, 7, 3, 2});
        System.out.println(JSON.toJSON(sort));
    }
}
