package com.clx.clxdash.Netty;


//记录调用方法的元信息的类




import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;


//多线程共享
@Component
@ChannelHandler.Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {

    //日志处理
    private Logger logger = LoggerFactory.getLogger(ServerChannelHandlerAdapter.class);



    //注入请求分排器
    @Resource
    private RequestDispatcher dispatcher;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);

        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);

        //MethodInvokeMeta invokeMeta = msg;

        //Fix Me !
        //处理数据




        System.out.println(msg);




        //dispatcher.




    }
}
