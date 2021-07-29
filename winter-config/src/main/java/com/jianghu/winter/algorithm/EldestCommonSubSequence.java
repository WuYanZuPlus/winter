package com.jianghu.winter.algorithm;

/**
 * 最长公共子序列
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * <p>
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *
 * @Author: daniel.hu
 * @Date: 2021/7/27 20:03
 */
public class EldestCommonSubSequence {

    public static int getSubSequence(String a, String b) {
        int m = a.length() + 1;
        int n = b.length() + 1;
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            matrix[i][0] = 0;
        }
        for (int j = 0; j < n; j++) {
            matrix[0][j] = 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int max = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                matrix[i][j] = a.charAt(i - 1) == b.charAt(j - 1) ? Math.max(max, matrix[i - 1][j - 1] + 1) : max;
            }
        }
        return matrix[m - 1][n - 1];
    }
}
