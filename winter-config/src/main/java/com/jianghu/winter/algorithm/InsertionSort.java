package com.jianghu.winter.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 插入排序
 *
 * @Author: daniel.hu
 * @Date: 2021/7/27 17:02
 */
public class InsertionSort {

    public static int[] insertSort(int[] input) {
        int l = input.length;
        if (l == 1) {
            return input;
        }
        for (int i = 0; i < l; i++) {
            for (int j = i; j > 0; j--) {
                if (input[j - 1] > input[j]) {
                    int temp = input[j - 1];
                    input[j - 1] = input[j];
                    input[j] = temp;
                }
            }
        }
        return input;
    }

    public static void main(String[] args) {
        int[] result = insertSort(new int[]{6, 5, 3, 8, 7, 2, 4});
        System.out.println(JSON.toJSONString(result));
    }
}
