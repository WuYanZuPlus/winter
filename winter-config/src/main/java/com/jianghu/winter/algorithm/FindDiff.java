package com.jianghu.winter.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字？
 * 分组异或
 *
 * @Author: daniel.hu
 * @Date: 2021/7/29 17:18
 */
public class FindDiff {

    public static int[] findTwoDiff(int[] input) {
        if (input.length < 2) {
            System.out.println("数组长度必须大于等于2");
        }
        int result = 0;
        for (int i = 0; i < input.length; i++) {
            result = result ^ input[i];
        }
        int x = 1;
        while ((result & x) == 0) {
            x = x << 1;
        }
        int a = 0;
        int b = 0;
        for (int i = 0; i < input.length; i++) {
            if ((input[i] & x) != 0) {
                a = a ^ input[i];
            } else {
                b = b ^ input[i];
            }
        }
        return new int[]{a, b};
    }

    public static void main(String[] args) {
//        int[] twoDiff = findTwoDiff(new int[]{3, 4, 8, 3, 2, 2,});
//        System.out.println(JSON.toJSONString(twoDiff));

        int[] twoDiff1 = findTwoDiff(new int[]{1, 2, 5, 2});
        System.out.println(JSON.toJSONString(twoDiff1));

    }
}
