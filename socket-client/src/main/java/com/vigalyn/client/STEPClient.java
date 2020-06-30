package com.vigalyn.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @description:
 * @projectName:vigalyn-socket-demo
 * @see:com.vigalyn.client
 * @author:曾维嘉
 * @createTime:2020/6/29 14:46
 * @version:1.0
 */
public class STEPClient {

    public static void main(String[] args) throws IOException {
        String serverIp = "192.10.30.137";
        int serverPort = 9999;
        // 创建Socket，并连接服务器
        Socket socket = new Socket(serverIp,serverPort);

        // 输出
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.write("Tag 34/MsgSeqNum=1");


    }
}
