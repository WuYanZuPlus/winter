package com.jianghu.winter.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: daniel.hu
 * @Date: 2021/4/23 17:10
 */
public class Palindromic {
    public static String getTheLongestString(String input) {
        List<String> groups = new ArrayList<>();
        if(input.length() ==1){
            groups.add(input);
        }
        return groups.get(0);
    }
}
