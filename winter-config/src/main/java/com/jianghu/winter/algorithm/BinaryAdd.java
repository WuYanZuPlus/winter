package com.jianghu.winter.algorithm;

/**
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 * 输入为 非空 字符串且只包含数字 1 和 0
 *
 * @Author: daniel.hu
 * @Date: 2021/7/30 14:04
 */
public class BinaryAdd {
    public static String addBinary(String a, String b) {
        if (a.length() < b.length()) {
            String temp = a;
            a = b;
            b = temp;
        }
        while ((a.length() - b.length()) > 0) {
            b = 0 + b;
        }
        int l = a.length();
        String ret = "";
        boolean intoOne = false;
        for (int i = 1; i < l + 1; i++) {
            int va = a.charAt(l - i) - '0';
            int vb = b.charAt(l - i) - '0';
            if ((va & vb) == 1) {
                ret = intoOne ? 1 + ret : 0 + ret;
                intoOne = true;
            } else {
                int x = va | vb;
                if (intoOne) {
                    if (x == 0) {
                        ret = 1 + ret;
                        intoOne = false;
                    } else {
                        ret = 0 + ret;
                        intoOne = true;
                    }
                } else {
                    ret = x + ret;
                    intoOne = false;
                }
            }
        }
        if (intoOne) {
            ret = 1 + ret;
        }
        return ret;
    }


    public static void main(String[] args) {
        String s = addBinary("1", "111");
        System.out.println(s);
    }
}
