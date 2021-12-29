package com.jianghu.winter.mq;

import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.System.out;

/**
 * 消息处理中心
 */
public class Broker {
    /**
     * 设置存储消息的最大数量
     */
    private static final int MAX_SIZE = 5;
    /**
     * 保存消息的容器
     */
    private static final ArrayBlockingQueue<String> MASSAGE_QUEUE = new ArrayBlockingQueue<>(MAX_SIZE);

    /**
     * 生产消息
     */
    public static void produce(String msg) {
        if (MASSAGE_QUEUE.offer(msg)) {
            out.println("成功向消息中心投递消息：" + msg + "，当前暂存消息数目为" + MASSAGE_QUEUE.size());
        } else {
            out.println("消息中心已满，不能继续放入消息！");
        }
        out.println("==================================");
    }

    /**
     * 消费消息
     */
    public static String consume() {
        String msg = MASSAGE_QUEUE.poll();
        if (msg != null) {
            out.println("已经消费消息：" + msg + "，当前暂存消息数目为" + MASSAGE_QUEUE.size());
        } else {
            out.println("消息处理中心已经没有消息可供消费！");
        }
        out.println("==================================");
        return msg;
    }
}