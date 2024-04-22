package cn.guruguru.coding.netty.quickstart.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientService {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventGroup = new NioEventLoopGroup();
        try {
            // 创建客户端引导对象
            Bootstrap bootstrap = new Bootstrap();
            // 设置线程组
            bootstrap.group(eventGroup)
                    // 设置客户端通道类型
                    .channel(NioSocketChannel.class)
                    // 使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加客户端通道的处理器
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            System.out.println("~ 客户端准备就绪 ~");
            // 连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            // 对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭线程组；如果服务端没有启动，或者服务端被迫停止，客户端也会跟着停止
            eventGroup.shutdownGracefully();
        }
    }
}
