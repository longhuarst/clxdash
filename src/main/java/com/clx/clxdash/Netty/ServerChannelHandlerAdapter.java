package com.clx.clxdash.Netty;


//记录调用方法的元信息的类




import com.clx.clxdash.Netty.Table.ListenTable;
import com.clx.clxdash.Spring.SpringUtil;
import com.clx.clxdash.jpa.MessageEntity;
import com.clx.clxdash.message.MessageRepository;
import com.clx.clxdash.uuid.Uuid;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


//多线程共享
//@Component    //不能为组件    不然会在springboot 加载的时候，调用构造函数， 该调用可能早于依赖，导致程序异常退出。
@ChannelHandler.Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {





//    @Autowired
    private static MessageRepository messageRepository;
    static {
        messageRepository = SpringUtil.getBean(MessageRepository.class);
    }




    //初始化Repository
    public void initRepository(){

        messageRepository = SpringUtil.getBean(MessageRepository.class);
    }


    @PostConstruct
    public void init(){
        logger.info("初始化.....");

    }




    //日志处理
    private Logger logger = LoggerFactory.getLogger(ServerChannelHandlerAdapter.class);



    //注入请求分排器
//    @Resource
//    private RequestDispatcher dispatcher;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);

        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        //super.channelRead(ctx, msg);

        //MethodInvokeMeta invokeMeta = msg;

        //Fix Me !
        //处理数据



        String megstr = message.toString();

        //v1版本协议
        if(megstr.startsWith("v1/pub/")){
//            logger.info("v1版本协议发布消息");


            String body = megstr.substring("v1/pub/".length());

//            logger.info("body="+body);


            String topic="";
            String msg = "";
            String uuid = "";


            //直接分割

            String result[] = body.split("/");

            if (result.length == 3){
                if (!result[0].equals("")){

                    //匹配成功
                    uuid = result[0];
                    topic = result[1];
                    msg = result[2];


//                    logger.info("uuid:"+uuid);
//                    logger.info("topic:"+topic);
//                    logger.info("msg:"+msg);

                    //鉴别uuid
                    if (Uuid.isValidUUID(uuid)){
                        //转发
                        //发布消息
                        Set<ChannelHandlerContext> sender = ListenTable.getInstance().get(uuid);

                        if (sender != null){
                            //有发送者
                            for (ChannelHandlerContext client : sender){
                                String message_sender = megstr + "\r\n";
                                ByteBuf respone = Unpooled.copiedBuffer(message_sender.getBytes());
                                client.writeAndFlush(respone);
                            }
                        }

                        if (sender == null) {
                            return;
                        }

                        MessageEntity messageEntity = new MessageEntity();

                        messageEntity.setUuid(uuid);
                        messageEntity.setTopic(topic);
                        messageEntity.setMsg(msg);
                        messageEntity.setTime(new Timestamp(new Date().getTime()));

                        NettyRepository repository = new NettyRepository();

                        messageRepository.save(messageEntity);

//                        logger.info("数据入库...");

                    }else{
//                        logger.info("uuid 格式不正确");
                    }



                }else{
//                    logger.info("uuid信息体为空："+result[0]);
                }
            }else{
//                logger.info("信息体数量为："+result.length);
            }
        }else if (megstr.startsWith("v1/sub/")){
//            logger.info("v1版本协议订阅消息");


            String body = megstr.substring("v1/sub/".length());

//            logger.info("body="+body);


            String uuid="";


            uuid = body;


            //鉴别uuid
            if (Uuid.isValidUUID(uuid)){
                //先移除

                ListenTable.getInstance().remove(ctx);

                //在增加

                ListenTable.getInstance().add(ctx, uuid);
            }else{
//                logger.info("uuid 格式不正确");
            }







        }





//        logger.info("ctx="+ctx.toString()+" | msg="+message);
//
//
//
//        logger.info(String.valueOf(message));




        //dispatcher.




    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        ListenTable.getInstance().remove(ctx);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);



        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    //读超时
                    logger.info("["+ctx.channel().remoteAddress()+"] Read Timeout !");

                    ctx.close();//关闭连接

                    break;

                case WRITER_IDLE:
                    //写超时
                    logger.info("["+ctx.channel().remoteAddress()+"] Write Timeout !");

                    break;

                case ALL_IDLE:
                    logger.info("["+ctx.channel().remoteAddress()+"] All Timeout !");

                    //全部超时
                    break;
                default:
                    break;
            }

        }



    }
}
