package com.jianghu.winter.algorithm;

/**
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *
 * 编辑距离
 *
 * @Author: daniel.hu
 * @Date: 2021/7/22 19:24
 */
public class Distance {

    public static int getDistance(String si, String sj) {
        if (si == null || sj == null) {
            return 0;
        }
        int m = si.length();
        int n = sj.length();
        if (m == 0 && n == 0) {
            return 0;
        }
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }
        int[][] matrix = new int[m][n];
        matrix[0][0] = si.charAt(0) == sj.charAt(0) ? 0 : 1;
        for (int i = 1; i < m; i++) {
            if (si.charAt(i) == sj.charAt(0)) {
                matrix[i][0] = matrix[i - 1][0];
            } else {
                matrix[i][0] = matrix[i - 1][0] + 1;
            }
        }
        for (int j = 1; j < n; j++) {
            if (si.charAt(0) == sj.charAt(j)) {
                matrix[0][j] = matrix[0][j - 1];
            } else {
                matrix[0][j] = matrix[0][j - 1] + 1;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int min = Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1);
                matrix[i][j] = si.charAt(i) == sj.charAt(j) ? Math.min(min, matrix[i - 1][j - 1]) : Math.min(min, matrix[i - 1][j - 1] + 1);
            }
        }
        return matrix[m-1][n-1];
    }

    public static void main(String[] args) {
        int distance = getDistance("pneumonoultramicroscopicsilicovolcanoconiosis", "ultramicroscopically");
        System.out.println(distance);

        int distance2 = getDistance("intention", "execution");
        System.out.println(distance2);
    }
}
