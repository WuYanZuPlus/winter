package com.jianghu.winter.fib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: daniel.hu
 * @Date: 2021/5/14 13:36
 */
public class FibonacciTest {
    @Test
    public void test_1() {
        assertFalse(Fibonacci.isLegal(new Integer[]{1}));
    }

    @Test
    public void test_1_1() {
        assertTrue(Fibonacci.isLegal(new Integer[]{1, 1}));
    }

    @Test
    public void test_2_2() {
        assertFalse(Fibonacci.isLegal(new Integer[]{2, 2}));
    }

    @Test
    public void test_8_13() {
        assertTrue(Fibonacci.isLegal(new Integer[]{8, 13}));
    }

    @Test
    public void test_5_6() {
        assertFalse(Fibonacci.isLegal(new Integer[]{5, 6}));
    }

    @Test
    public void test_8_13_22() {
        assertFalse(Fibonacci.isLegal(new Integer[]{8, 13, 22}));
    }
}