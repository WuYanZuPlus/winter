package com.jianghu.winter.mq;

public class ConsumeClient {
    public static void main(String[] args) throws Exception {
        String message = MQClient.consume();
        System.out.println("获取的消息为："+message);
    }
}