package com.clx.clxdash.Netty;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//请求分排器
@Component
public class RequestDispatcher implements ApplicationContextAware {



    private ExecutorService executorService = Executors.newFixedThreadPool(NettyConstant.getMaxThreads());
    private ApplicationContext app;



    //发送
    public void dispatcher(final ChannelHandlerContext ctx, final MethodInvokeMeta invokeMeta){
        executorService.submit(() -> {
            ChannelFuture f = null;


            try{
                Class<?> interfaceClass = invokeMeta.getInterfaceClass();

                String name = invokeMeta.getMethodName();
                Object[] args = invokeMeta.getArgs();
                Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
                Object targetObject = app.getBean(interfaceClass);
                Method method = targetObject.getClass().getMethod(name, parameterTypes);
                Object obj = method.invoke(targetObject, args);
                if (obj == null){
                    f = ctx.writeAndFlush(NullWritable.nullWritable());
                }else{
                    f = ctx.writeAndFlush(obj);
                }
                f.addListener(ChannelFutureListener.CLOSE);

            }catch (Exception e){
                //ResponseResult error = ResponseResultUtil.error(ResponseCodeEnum.SERVER_ERROR);
                f = ctx.writeAndFlush(e.getMessage());
            } finally {
                f.addListener(ChannelFutureListener.CLOSE);
            }

        });
    }


    //加载当前application.xml
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.app = ctx;
    }
}


