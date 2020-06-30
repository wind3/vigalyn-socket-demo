package com.vigalyn.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @projectName:vigalyn-socket-demo
 * @see:com.vigalyn.server
 * @author:曾维嘉
 * @createTime:2020/6/30 15:41
 * @version:1.0
 */
public class ServerSocketChannelServer {

    public static void main(String[] args) throws IOException {

        // 创建 服务器通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 创建 多路复用选择器
        Selector selector = Selector.open();
        // 修改 服务器通道为 非阻塞
        ssChannel.configureBlocking(false);
        // 注册 服务器通道 到 多路复用选择器，等待 接收（请求）就绪事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 创建 服务器套接字
        ServerSocket serverSocket = ssChannel.socket();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 12345);
        // 绑定监听端口
        serverSocket.bind(address);

        while (true){
            // 阻塞，直到有通道在选择器上 就绪
            selector.select();
            // 获取就绪的 selectionKey
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            // 遍历
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                // 如果是 接收就绪
                if (key.isAcceptable()){
                    // 获取 服务器通道
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 接受并建立 客户端通道
                    SocketChannel cChannel = server.accept();
                    // 修改 客户端通道为 非阻塞
                    cChannel.configureBlocking(false);
                    // 注册 客户端通道 到多路复用选择器，等待 读（请求）就绪事件
                    cChannel.register(selector, SelectionKey.OP_READ);

                    //如果是 读就绪
                } else if(key.isReadable()){
                    // 获取 客户端通道
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 建立 A字节缓存
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    StringBuilder stringBuilder = new StringBuilder();
                    while (true){
                        byteBuffer.clear();
                        int n = socketChannel.read(byteBuffer);
                        // 如果读取结束，则退出
                        if ( -1 == n){
                            break;
                        }
                        byteBuffer.flip();
                        // 获取当前写入的长度
                        int limit = byteBuffer.limit();
                        char[] chars = new char[limit];
                        for (int i = 0; i < limit; i++) {
                            // 将字节 转为 字符
                            chars[i] = (char) byteBuffer.get(i);
                        }
                        // 将字节数组 添加到 字符串
                        stringBuilder.append(chars);
                        // 清空缓存，继续读
                        byteBuffer.clear();
                    }
                    // 输出 请求信息
                    System.out.println( stringBuilder.toString() );
                    // 关闭客户端通道
                    socketChannel.close();
                }
                // 删除 已处理的 selectedKey
                iterator.remove();
            }
        }
    }
}
