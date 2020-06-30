package com.vigalyn.client;

import java.io.*;
import java.net.Socket;

/**
 * @description:
 * @projectName:vigalyn-socket-demo
 * @see:com.vigalyn.client
 * @author:曾维嘉
 * @createTime:2020/6/29 11:56
 * @version:1.0
 */
public class CustomClient {

    /**
     * description
     * 1、创建Socket对象，绑定服务器IP + PORT
     * 2、建立连接
     * 3、通过输出流，想服务器发送请求消息
     * 4、通过输入流，获取服务器响应消息
     * param
     * return
     * author 曾维嘉
     * createTime
     **/
    public static void main(String[] args) throws IOException {
        // 创建Socket，并连接服务器
        Socket socket = new Socket("localhost",12345);

        // 键盘输入缓冲
        BufferedReader sysIn = new BufferedReader( new InputStreamReader( System.in));
        // socket输入缓冲
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream()));
        // 输出
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        // 从键盘输入
        String readLine = sysIn.readLine();
        while (! readLine.equals("bye")){
            printWriter.println(readLine);
            printWriter.flush();
            System.out.println("client: " + readLine);

            System.out.println("server: " + bufferedReader.readLine());
            readLine = sysIn.readLine();
        }

        //关闭IO和Socket
        sysIn.close();
        printWriter.close();
        socket.close();

    }
}
