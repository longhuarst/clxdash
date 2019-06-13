package com.clx.clxdash.Netty;


import com.fasterxml.jackson.core.ObjectCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class NettyServerListener {




    //NettyServerLinstener 日志输出器
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerListener.class);



    //创建bootstrap
    ServerBootstrap serverBootstrap = new ServerBootstrap();



    //boss
    EventLoopGroup boss = new NioEventLoopGroup();


    //worker
    EventLoopGroup work = new NioEventLoopGroup();


    //通道适配器
//    @Resource
//    private ServerChannelHandlerAdapter channelHandlerAdapter;



    //netty 服务器配置类
//    @Resource NettyConfig nettyConfig;


    //关闭服务器方法
    @PreDestroy
    public void close(){
        LOGGER.info("关闭服务器...");

        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();

    }



    //开启及服务线程
    public void start(){

        //从配置文件(application.yml)中获取服务端监听的端口号






        int port = 19000;
//        int port = nettyConfig.getPort();

        serverBootstrap.group(boss, work)
            .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler(LogLevel.INFO));


        System.out.println("-------开始初始化Netty--------");


        try{
            //设置事件处理

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
//
//                    pipeline.addLast(new LengthFieldBasedFrameDecoder(NettyConstant.getMaxFrameLength(), 0, 2, 0, 2));
//                    pipeline.addLast(new LengthFieldPrepender(2));
//                    //pipeline.addLast(new ObjectCodec());
//




                    pipeline.addLast(new IdleStateHandler(60, 80, 90, TimeUnit.SECONDS));
//在原来的之前新增了两个解码器LineBasedFrameDecoder、StringDecoder
                    pipeline.addLast(new LineBasedFrameDecoder(1024));
                    pipeline.addLast(new StringDecoder());


                    pipeline.addLast( new ServerChannelHandlerAdapter());


                }
            });


            LOGGER.info("netty服务器[{}]端口启动监听...", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();


            LOGGER.info("-----------bind()-------------");

            f.channel().closeFuture().sync();


            LOGGER.info("-----------netty-------------");

        }catch (InterruptedException e){
            LOGGER.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }





}














