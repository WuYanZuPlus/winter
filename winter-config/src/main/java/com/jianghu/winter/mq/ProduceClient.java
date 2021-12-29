package com.jianghu.winter.mq;

public class ProduceClient {
    public static void main(String[] args) throws Exception {
        MQClient.produce("Hello World4!!");
    }
}