package com.jianghu.winter.algorithm;

/**
 * @Author: daniel.hu
 * @Date: 2021/8/24 16:50
 */
public class FenFaTangGuo {

    /**
     * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
     *
     * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
     *
     * 每个孩子至少分配到 1 个糖果。
     * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
     * 那么这样下来，老师至少需要准备多少颗糖果呢？
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/candy
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {
        int length = ratings.length;
        int[] left = new int[length];
        for (int i = 0; i < ratings.length; i++) {
            if (i > 0 && ratings[i - 1] < ratings[i]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0;
        int ret = 0;
        for (int i = length - 1; i >= 0; i--) {
            if (i < length - 1 && ratings[i + 1] < ratings[i]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }

    public static void main(String[] args) {
        candy(new int[]{1, 0, 2});
    }
}
