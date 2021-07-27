package com.jianghu.winter.test;

import com.alibaba.fastjson.JSON;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.util.IterableUtil.iterator;

/**
 * @Author: daniel.hu
 * @Date: 2021/7/1 19:35
 */
public class LinkList<E> {
    int size = 0;
    Node<E> first;
    Node<E> last;

    public LinkList() {
    }

    static class Node<E> {
        Node<E> prev;
        E v;
        Node<E> next;

        public Node(Node<E> prev, E v, Node<E> next) {
            this.prev = prev;
            this.v = v;
            this.next = next;
        }
    }

    public void add(E e) {
        final Node<E> l = last;
        Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public static void main(String[] args) {
        LinkList<Integer> list = new LinkList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.size);
        System.out.println(JSON.toJSONString(list));
    }



}
