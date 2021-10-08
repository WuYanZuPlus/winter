package com.jianghu.winter.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

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
//        int a = singleNumber(new int[]{0, 1, 0, 1, 0, 1, 99});
//        System.out.println(a);

//        int i = countPrimeSetBits(6, 10);
//        System.out.println(i);

//        System.out.println(1 << 31);
        int[] duplicate = twoSum(new int[]{3, 3}, 6);
        System.out.println(JSON.toJSONString(duplicate));

    }

    public static int countPrimeSetBits(int left, int right) {
        int total = 0;
        for (int i = left; i <= right; i++) {
            int singleTotal = 0;
            int x = 1;
            for (int j = 0; j < 32; j++) {
                if ((i & x) != 0) {
                    singleTotal++;
                }
                x = x << 1;
            }
            if (isPrimeNormal(singleTotal)) {
                total++;
            }
        }
        return total;
    }

    public static boolean isPrimeNormal(int num) {
        if (num >= 2) {
            for (int i = 2; i < num; i++) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static int reverseBits(int n) {
        int a = 0;
        int x = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & x) != 0) {
                a = a | (1 << 31 - i);
            }
            x = x << 1;
        }
        return a;
    }

    public static int findDuplicate(int[] nums) {
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            x = x ^ nums[i];
        }
        return 0;
    }

    public static int thirdMax(int[] nums) {
        int[] ret = new int[3];
        int n = 0;
        for (int i = 0; i < 3; i++) {
            ret[i] = Integer.MIN_VALUE;
            for (int j = 0; j < nums.length; j++) {
                if (i == 0 || nums[j] < ret[i - 1]) {
                    ret[i] = Math.max(nums[j], ret[i]);
                    n = i;
                }
            }
        }
        return n == 2 ? ret[2] : ret[0];
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            int key = map.get(target-nums[i]);
            if(map.containsKey(key)){
                return new int[]{i,map.get(key)};
            }
            map.put(nums[i],i);
        }
        return new int[]{};
    }


}
