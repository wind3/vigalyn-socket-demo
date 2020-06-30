package com.vigalyn.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @description:
 * @projectName:vigalyn-socket-demo
 * @see:com.vigalyn.client
 * @author:曾维嘉
 * @createTime:2020/6/30 15:40
 * @version:1.0
 */
public class SocketChannelClient {

    public static void main(String[] args) throws IOException {
        {
            Socket socket = new Socket("127.0.0.1",12345);
            OutputStream out = socket.getOutputStream();
            String string = "hello wind3";
            out.write(string.getBytes());
            out.close();
            socket.close();
        }
    }
}
