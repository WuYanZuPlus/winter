package com.jianghu.winter.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: daniel.hu
 * @Date: 2021/9/29 10:32
 */
public class LeetCodeAll {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int second = target - nums[i];
            if (map.containsKey(second)) {
                return new int[]{i, map.get(second)};
            }
            map.put(i, nums[i]);
        }
        return null;
    }

    /**
     * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
     * <p>
     * 请你返回该链表所表示数字的 十进制值 。
     * <p>
     *  
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/convert-binary-number-in-a-linked-list-to-integer
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param head
     * @return
     */
    public int getDecimalValue(SingleNode.ListNode head) {
        int a = 0;
        while (head != null) {
            a = a * 2 + head.val;
            head = head.next;
        }
        return a;
    }

    /**
     * 双指针
     * <p>
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * <p>
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * <p>
     *  
     * <p>
     * 作者：力扣 (LeetCode)
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xnhbqj/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param s
     */
    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }
    }
}
