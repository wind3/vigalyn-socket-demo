package com.vigalyn.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @projectName:vigalyn-socket-demo
 * @see:com.vigalyn.server
 * @author:曾维嘉
 * @createTime:2020/6/29 11:56
 * @version:1.0
 */
public class CustomServer {

    /**
     * description
     * 1、创建ServerSocket对象，绑定监听端口
     * 2、调用accept()方法，监听客户端请求
     * 3、建立连接后，通过输入流读取客户端发送的请求数据
     * 4、通过输出流，想客户端发送响应数据
     * 5、关闭相关资源
     * param
     * return
     * author 曾维嘉
     * createTime
     **/
    public static void main(String[] args) throws IOException {
        // 创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(12345);
        InetAddress localHost = InetAddress.getLocalHost();
        String ip = localHost.getHostAddress();

        Socket socket = null;
        // 调用accept()，等待客户端连接
        socket = serverSocket.accept();

        // 键盘输入缓冲
        BufferedReader sysIn = new BufferedReader( new InputStreamReader( System.in));
        // 获取输入流，读取客户端发送的信息
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream()));
        // 输出
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        String info = sysIn.readLine();
        while ( ! info.equals("bye") ){
            String clientInfo = bufferedReader.readLine();
            System.out.println(clientInfo);

            printWriter.println(info);
            printWriter.flush();
            System.out.println("server：" + info);
            info = sysIn.readLine();
        }

        //关闭IO、Socket
        sysIn.close();
        bufferedReader.close();
        printWriter.close();
        socket.shutdownInput();
        socket.close();
        serverSocket.close();
    }
}
