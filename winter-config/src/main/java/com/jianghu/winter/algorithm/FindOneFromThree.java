package com.jianghu.winter.algorithm;

/**
 * 给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
 *
 * @Author: daniel.hu
 * @Date: 2021/8/2 14:54
 */
public class FindOneFromThree {

    public static int singleNumber(int[] nums) {
        int a = 0;
        int x = 1;
        for (int i = 0; i < 32; i++) {
            int total = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((nums[j] & x) != 0) {
                    total++;
                }
            }
            int mod = total % 3;
            a = a | (mod << i);
            x = x << 1;
        }
        return a;
    }

    public static void main(String[] args) {
        int a = singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99});
        System.out.println(a);
    }
}
