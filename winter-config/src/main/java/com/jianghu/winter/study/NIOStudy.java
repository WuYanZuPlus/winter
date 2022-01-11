package com.jianghu.winter.study;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件拷贝类
 * @Author: daniel.hu
 * @Date: 2022/1/11 15:07
 */
public class NIOStudy {

    /**
     * NIO文件拷贝
     *
     * @param srcFileName
     * @param targetFileName
     * @throws IOException
     */
    public static void copyFile(String srcFileName, String targetFileName) throws IOException {
        FileInputStream fis = new FileInputStream(srcFileName);
        FileOutputStream fos = new FileOutputStream(targetFileName);
        FileChannel readChannel = fis.getChannel();
        FileChannel writeChannel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            // 清空缓存区
            buffer.clear();
            // 从输入channel中读取数据到buffer中
            if (readChannel.read(buffer) == -1) {
                break;
            }
            // 将缓存区游标置0
            buffer.flip();
            // 将缓存中数据写到channel中
            writeChannel.write(buffer);
        }
        fis.close();
        fos.close();
    }


    /**
     * 字节流（使用FileInputStream和FileOutputStream读取每一个字节...)
     *
     * @param src
     * @param des
     * @throws Exception
     */
    @Deprecated
    public static void copy1(String src, String des) throws Exception {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(des);
        int b = 0;
        while ((b = fis.read()) != -1) {
            fos.write(b);
            fos.flush();
        }
        fis.close();
        fos.close();
    }


    /**
     * 字节流（使用FileInputStream和FileOutputStream一次读取一个byte数组...）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    public static void copy2(String src, String des) throws Exception {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(des);
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            fos.write(b, 0, len);
            fos.flush();
        }
        fis.close();
        fos.close();
    }

    /**
     * 高效字节缓冲流（使用BufferedInputStream和BufferedOutputStream一次性读取一个字节）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    @Deprecated
    public static void copy3(String src, String des) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
        int b = 0;
        while ((b = bis.read()) != -1) {
            bos.write(b);
            bos.flush();
        }
        bis.close();
        bos.close();
    }

    /**
     * 高效字节缓冲流（使用BufferedInputStream和BufferedOutputStream一次性读取一个字节数组）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    public static void copy4(String src, String des) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = bis.read(b)) != -1) {
            bos.write(b, 0, len);
            bos.flush();
        }
        bis.close();
        bos.close();
    }

    /**
     * 字符流（使用FileReader和FileWriter读取每一个字符）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    @Deprecated
    public static void copy5(String src, String des) throws Exception {
        FileReader fr = new FileReader(src);
        FileWriter fw = new FileWriter(des);
        int b = 0;
        while ((b = fr.read()) != -1) {
            fw.write(b);
            fw.flush();
        }
        fr.close();
        fw.close();
    }

    /**
     * 字符流（使用FileReader和FileWriter读取每一个字符数组）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    public static void copy6(String src, String des) throws Exception {
        FileReader fr = new FileReader(src);
        FileWriter fw = new FileWriter(des);
        int len = 0;
        char[] b = new char[1024];
        while ((len = fr.read(b)) != -1) {
            fw.write(b, 0, len);
            fw.flush();
        }
        fr.close();
        fw.close();
    }


    /**
     * 高效字符缓冲流（使用BufferedReader和BufferedWriter读取每一个字符）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    public static void copy7(String src, String des) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(src));
        BufferedWriter bw = new BufferedWriter(new FileWriter(des));
        int b = 0;
        while ((b = br.read()) != -1) {
            bw.write(b);
            bw.flush();
        }
        br.close();
        bw.close();
    }

    /**
     * 高效字符缓冲流（使用BufferedReader和BufferedWriter读取每一个字符数组）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    public static void copy8(String src, String des) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(src));
        BufferedWriter bw = new BufferedWriter(new FileWriter(des));
        int len = 0;
        char[] b = new char[1024];
        while ((len = br.read(b)) != -1) {
            bw.write(b, 0, len);
            bw.flush();
        }
        br.close();
        bw.close();
    }

    /**
     * 高效字符缓冲流（使用BufferedReader和BufferedWriter读取每一行）
     *
     * @param src
     * @param des
     * @throws Exception
     */
    public static void copy9(String src, String des) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(src));
        BufferedWriter bw = new BufferedWriter(new FileWriter(des));
        String s = null;
        while ((s = br.readLine()) != null) {
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        br.close();
        bw.close();
    }

}
