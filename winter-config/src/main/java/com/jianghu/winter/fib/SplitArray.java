package com.jianghu.winter.fib;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: daniel.hu
 * @Date: 2021/5/18 16:51
 */
public class SplitArray {
    public static boolean canSplitTwoPart(int[] arrays) {
        int total = 0;
        int length = arrays.length;
        for (int i = 0; i < length; i++) {
            total += arrays[i];
        }
        if (total % 2 != 0) {
            return false;
        }
        int half = total / 2;
        Arrays.sort(arrays);
        int max = arrays[length - 1];
        List<Integer> oneList = new ArrayList<>();
        if (max > half) {
            return false;
        }
        if (max == half) {
            oneList.add(max);
            System.out.println("其中一个子集为：" + JSON.toJSONString(oneList));
            return true;
        }
        if (max < half) {
            int diff = half - max;
//            if()
        }
        return false;
    }
}
