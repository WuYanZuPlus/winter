package com.jianghu.winter.algorithm;

/**
 * @Author: daniel.hu
 * @Date: 2021/8/19 19:32
 */
public class MyLinkedList {
    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {

    }

    int size;
    Node first;

    static class Node {
        int val;
        Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        int s = 0;
        Node head = first;
        while (s != index && s < size) {
            head = head.next;
            s++;
        }
        return head.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node h = first;
        first = new Node(val, h);
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        int s = 0;
        Node h = first;
        while (s < size - 1) {
            h = h.next;
            s++;
        }
        h.next = new Node(val, null);
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) {
            return;
        }
        if (index == 0) {
            addAtHead(val);
        } else if (index == size) {
            addAtTail(val);
        } else {
            Node h = first;
            for (int i = 1; i < index; i++) {
                h = h.next;
            }
            Node n = h.next;
            h.next = new Node(val, null);
            if (n != null) {
                h.next.next = n;
            }
            size++;
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        if (index == 0) {
            first = null;
            return;
        }
        Node h = first;
        for (int i = 1; i < index; i++) {
            h = h.next;
        }
        h.next = h.next.next;
        size--;
    }


    public static void main(String[] args) {
        MyLinkedList obj = new MyLinkedList();
        obj.addAtHead(1);
        obj.addAtTail(3);
        obj.addAtIndex(1, 2);
        obj.deleteAtIndex(2);
        obj.addAtTail(4);
        System.out.println(obj.get(2));
    }
}
