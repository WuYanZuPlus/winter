package com.jianghu.winter.algorithm;

/**
 * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
 *
 *
 * @Author: daniel.hu
 * @Date: 2021/8/2 10:28
 */
public class HammingWeightC {
    public int hammingWeight(int n) {
        int ret = 0;
        int x = 1;
        for (int i = 0; i < 32; i++) {
            if((n & x) != 0){
                ret++;
            }
            x = x <<1;
        }
        return ret;
    }
}
