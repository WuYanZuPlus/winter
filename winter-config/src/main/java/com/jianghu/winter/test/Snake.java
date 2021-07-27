package com.jianghu.winter.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Snake extends JFrame implements KeyListener, ActionListener, MouseListener {
    // 蛇当前长度
    int snakeLength = 2;
    // 蛇坐标
    int[] snakeX = new int[100];
    int[] snakeY = new int[100];
    // 蛇的方向 0-左 1-右 2-上 3-下
    int direction = 1;
    // 设置定时器，每100毫秒一次
    Timer timer = new Timer(100, this);
    // 食物位置
    int foodX;
    int foodY;
    // 随机数，随机位置生成食物
    Random random = new Random();
    /**
     * 游戏信息 0-未开始 1-开始 2-结束
     */
    int state = 0;


    /**
     * 窗体
     */
    public void myJFrame() {
        this.setTitle("贪吃蛇");
        // 窗口大小
        this.setSize(800, 600);
        // 窗口是否可以改变大小=否
        this.setResizable(false);
        // 窗口关闭方式为关闭窗口同时结束程序
        this.setDefaultCloseOperation(Snake.EXIT_ON_CLOSE);
        int width = Toolkit.getDefaultToolkit().getScreenSize().width; //获取屏幕宽度
        int height = Toolkit.getDefaultToolkit().getScreenSize().height; //获取屏幕高度
        this.setLocation((width - 800) / 2, (height - 600) / 2); //设置窗口默认位置以屏幕居中

        this.setFocusable(true);
        this.addKeyListener(this);
        // 窗口是否显示=是
        this.setVisible(true);

        // 蛇的初识位置
        snakeX[0] = 60;
        snakeY[0] = 100;
        snakeX[1] = 40;
        snakeY[1] = 100;

//        随机食物的初识位置
        foodX = random.nextInt(39);
        foodY = random.nextInt(22);
        foodX = foodX * 20;
        foodY = foodY * 20 + 80;

        System.out.println(foodX + "，" + foodY);
    }


    //---------------------------------------------------------------------------------------------------------------------
    //覆写paint方法,绘制界面
    @Override
    public void paint(Graphics g) {
//        绘制背景
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 800, 600);

        //绘制游戏区域
        g.setColor(Color.black);
        g.fillRect(0, 80, 800, 520);

        //绘制蛇
        for (int i = 0; i < snakeLength; i++) {
            g.setColor(Color.CYAN);
            g.fillRect(snakeX[i], snakeY[i], 20, 20);
        }


        //        绘制食物
        g.setColor(Color.yellow);
        g.fillOval(foodX, foodY, 20, 20);


        if (state == 0) {
            g.setColor(Color.BLACK);//设置画笔颜色
            g.setFont(new Font("微软雅黑", 10, 20)); //设置字体
            g.drawString("按下“空格键”开始游戏", 300, 65); //绘制字符
        } else if (state == 1) {
            g.setColor(Color.BLACK);//设置画笔颜色
            g.setFont(new Font("微软雅黑", 10, 20)); //设置字体
            g.drawString("当前分数为：", 300, 65); //绘制字符
            g.drawString(String.valueOf(snakeLength - 2), 420, 65); //绘制字符
        } else if (state == 2) {
            g.setColor(Color.BLACK);//设置画笔颜色
            g.setFont(new Font("微软雅黑", 10, 20)); //设置字体
            g.drawString("游戏结束-", 250, 65); //绘制字符
            g.drawString("最终分数为：", 350, 65); //绘制字符
            g.drawString(String.valueOf(snakeLength - 2), 470, 65); //绘制字符
        }

    }


    //    操作监听——控制蛇的不断移动
    @Override
    public void actionPerformed(ActionEvent e) {
//        判断游戏是否开始
        if (state == 1) {
//            通过循环控制蛇移动
            for (int i = snakeLength - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }

//            判断蛇移动的方向
            if (direction == 0) {
                snakeX[0] = snakeX[0] - 20;
            } else if (direction == 1) {
                snakeX[0] = snakeX[0] + 20;
            } else if (direction == 2) {
                snakeY[0] = snakeY[0] - 20;
            } else if (direction == 3) {
                snakeY[0] = snakeY[0] + 20;
            }

//            判断蛇是否撞到墙外
            if (snakeX[0] < 0 || snakeX[0] > 780 || snakeY[0] < 80 || snakeY[0] > 580) {
                state = 2;
            }

//            判断蛇是否吃到了食物
            if (snakeX[0] == foodX && snakeY[0] == foodY) {

                snakeLength++;

                foodX = random.nextInt(39);
                foodY = random.nextInt(22);
                foodX = foodX * 20;
                foodY = foodY * 20 + 80;

                System.out.println(foodX + "，" + foodY);
            }

//            判断是否吃到了自己
            for (int i = 1; i < snakeLength; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    state = 2;
                }
            }

//            判断食物是否随机在了蛇身上
            for (int i = 0; i < snakeLength; i++) {
                if (foodX == snakeX[i] && foodY == snakeY[i]) {

                    //        随机食物的初识位置
                    foodX = random.nextInt(39);
                    foodY = random.nextInt(22);
                    foodX = foodX * 20;
                    foodY = foodY * 20 + 80;

                    System.out.println(foodX + "，" + foodY);
                }
            }


            repaint();
        }
        timer.start();
    }

    //    输入
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //    键盘按下——控制游戏的开始以及蛇的移动方向
    @Override
    public void keyPressed(KeyEvent e) {
//        获取从键盘输入的键
        int key = e.getKeyCode();
//        判断是否为空格
        if (key == KeyEvent.VK_SPACE) {
            if (state == 0) {
                state = 1;
            } else if (state == 1) {
                state = 0;
            } else if (state == 2) {
                state = 0;

                snakeLength = 2;//蛇当前长度

                //        蛇的初识位置
                snakeX[0] = 60;
                snakeY[0] = 100;
                snakeX[1] = 40;
                snakeY[1] = 100;

                //        随机食物的初识位置
                foodX = random.nextInt(39);
                foodY = random.nextInt(22);
                foodX = foodX * 20;
                foodY = foodY * 20 + 80;

                //                初始化方向
                direction = 1;
            }

            repaint();
            timer.start();

//            左
        } else if (key == KeyEvent.VK_LEFT) {
            if (direction != 1) {
                direction = 0;
            }
//            右
        } else if (key == KeyEvent.VK_RIGHT) {
            if (direction != 0) {
                direction = 1;
            }
//            上
        } else if (key == KeyEvent.VK_UP) {
            if (direction != 3) {
                direction = 2;
            }
//下
        } else if (key == KeyEvent.VK_DOWN) {
            if (direction != 2) {
                direction = 3;
            }
        }

    }

    /**
     * 弹起
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 点击
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * 按下
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * 抬起
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * 进入
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * 离开
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}

