package com.clx.clxdash.Netty;


import com.clx.clxdash.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyRepository {


    @Autowired
    private MessageRepository messageRepository;




    public void save(){
//        System.out.println(messageRepository);
    }

}
