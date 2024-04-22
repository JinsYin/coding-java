package cn.guruguru.coding.netty.quickstart.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerService {

    public static void main(String[] args) throws Exception {
        //【多 Reactor 多线程】
        // 创建两个线程组 bossGroup、workerGroup，默认线程数是 CPU 核数两倍
        // bossGroup 用于监听客户端连接，专门负责与客户端创建连接，并把连接注册到 workerGroup 的 Selector 中
        // workerGroup 用于处理每一个连接发生的读写事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 相当于 Reactor 模式中的 mainReactor；可以传入线程数
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 相当于 Reactor 模式中的 subReactor
        try {
            // 创建服务端启动对象，设置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置两个线程组 bossGroup 和 workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // 设置服务端通道类型
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 使用匿名内部类的形式初始化通道对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 给 pipeline 管道设置处理器
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });// 给 workerGroup 的 EventLoop 对应的管道设置处理器
            System.out.println("~ 服务端准备就绪 ~");
            // 绑定端口号，启动服务端
            ChannelFuture channelFuture = bootstrap.bind(6666).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
