package com.clx.clxdash.message;



import com.clx.clxdash.jpa.MessageEntity;
import com.clx.clxdash.uuid.Uuid;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("message/")
public class MessageController  {



    @Autowired
    MessageRepository messageRepository;




    //发布消息
    @RequestMapping("publish")
    String publish(@RequestParam String  uuid, @RequestParam String topic, @RequestParam String msg){


        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setUuid(uuid);
        messageEntity.setTopic(topic);
        messageEntity.setMsg(msg);
        messageEntity.setTime(new Timestamp(new Date().getTime()));


        messageRepository.save(messageEntity);


        return "success";
    }




    @ResponseBody
    @RequestMapping(value="get",  produces = "application/json; charset=utf-8")
    public String get(){

        System.out.println("----json----");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("result","success");

        return jsonObject.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value="getjsonbyuuid", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    String getjsonbyuuid(@RequestBody JSONObject json){



        JSONObject jsonObject = new JSONObject();

        String uuid = "";
        uuid = json.get("uuid").toString();

        if (!Uuid.isValidUUID(uuid)){
            //无效的uuid

            jsonObject.put("result","0");

            return jsonObject.toJSONString();

        }

        System.out.println("uuid="+uuid);


        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setUuid(uuid);
        //messageEntity.setId(1);
        System.out.println(messageEntity.toString());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("uuid", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉


        Example<MessageEntity> example = Example.of(messageEntity,matcher);
//        messageEntity
        List<MessageEntity> messages = messageRepository.findAll(example);


        System.out.println("找到条目数: "+messages.size());

        System.out.println(messages.toString());





        for (int i=0; i<messages.size(); ++i) {
            JSONObject childObject = new JSONObject();

            childObject.put("topic",messages.get(i).getTopic());
            childObject.put("msg",messages.get(i).getMsg());

            System.out.println("time="+messages.get(i).getTime());

            childObject.put("time",messages.get(i).getTime().toString());

            jsonObject.put(String.valueOf(i),childObject);

        }





        JSONObject respone = new JSONObject();
        respone.put("result","success");
        respone.put("size",messages.size());

        respone.put("message",jsonObject);

        return respone.toString();
    }




    @RequestMapping("getbyuuid")
    String getbyuuid(@RequestParam String uuid){

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setUuid(uuid);
        //messageEntity.setId(1);
        System.out.println(messageEntity.toString());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("uuid", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉


        Example<MessageEntity> example = Example.of(messageEntity,matcher);
//        messageEntity
        List<MessageEntity> messages = messageRepository.findAll(example);


        System.out.println("找到条目数: "+messages.size());

        System.out.println(messages.toString());


        JSONObject jsonObject = new JSONObject();


       for (int i=0; i<messages.size(); ++i) {
            JSONObject childObject = new JSONObject();

            childObject.put("topic",messages.get(i).getTopic());
            childObject.put("msg",messages.get(i).getMsg());
            childObject.put("time",messages.get(i).getTime());

            jsonObject.put(String.valueOf(i),childObject);

        }






        return jsonObject.toString();
    }

}
