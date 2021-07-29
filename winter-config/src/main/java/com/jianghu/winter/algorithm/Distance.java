package com.jianghu.winter.algorithm;

/**
 * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 * <p>
 * 编辑距离
 *
 * @Author: daniel.hu
 * @Date: 2021/7/22 19:24
 */
public class Distance {

    public static int getDistance(String si, String sj) {
        int m = si.length() + 1;
        int n = sj.length() + 1;
        int[][] matrix = new int[m][n];
        matrix[0][0] = 0;
        for (int i = 0; i < m; i++) {
            matrix[i][0] = i;
        }
        for (int i = 0; i < n; i++) {
            matrix[0][i] = i;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int min = Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1);
                matrix[i][j] = si.charAt(i - 1) == sj.charAt(j - 1)
                        ? Math.min(min, matrix[i - 1][j - 1]) : Math.min(min, matrix[i - 1][j - 1] + 1);
            }
        }
        return matrix[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int distance = getDistance("pneumonoultramicroscopicsilicovolcanoconiosis", "ultramicroscopically");
        System.out.println(distance);

        int distance2 = getDistance("intention", "execution");
        System.out.println(distance2);
    }
}
