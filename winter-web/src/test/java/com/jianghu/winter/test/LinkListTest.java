package com.jianghu.winter.test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: daniel.hu
 * @Date: 2021/7/7 15:47
 */
public class LinkListTest {


    public static void main(String[] args) {
        LinkList<Integer> list = new LinkList<>();
        list.add(1);
        list.add(2);
        System.out.println(list.size);
    }

}