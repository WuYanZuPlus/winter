package com.jianghu.winter.mq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 消息队列服务
 */
public class BrokerServer implements Runnable {
    public static final int SERVICE_PORT = 9999;
    private final Socket socket;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
        ) {
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                System.out.println("接收到的原始数据为：" + str);
                //CONSUME表示要消费一条消息
                if (str.equals("CONSUME")) {
                    String msg = Broker.consume();
                    out.println(msg);
                    out.flush();
                } else {//其他情况都表示要生产消息到消息队列中
                    Broker.produce(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(BrokerServer.SERVICE_PORT);
        while (true) {
            BrokerServer bs = new BrokerServer(server.accept());
            new Thread(bs).start();
        }
    }
}