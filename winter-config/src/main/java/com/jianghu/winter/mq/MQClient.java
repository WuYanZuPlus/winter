package com.jianghu.winter.mq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MQClient {

    /**
     * 生产消息
     *
     * @param msg
     * @throws Exception
     */
    public static void produce(String msg) throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream());
        ) {
            out.println(msg);
            out.flush();
        }
    }

    /**
     * 消费消息
     *
     * @return
     * @throws Exception
     */
    public static String consume() throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            //先向消息队列发送CONSUME表示消费消息
            out.println("CONSUME");
            out.flush();
            //再从队列获取一条消息
            String message = in.readLine();
            return message;
        }
    }
}