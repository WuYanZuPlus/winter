package com.jianghu.winter.config.algorithm;

import com.jianghu.winter.algorithm.Palindromic;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @Author: daniel.hu
 * @Date: 2021/4/23 17:10
 */
public class PalindromicTest {

    public void test_one(){
        assertEquals("a", Palindromic.getTheLongestString("a"));
    }

}