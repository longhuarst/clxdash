package com.clx.clxdash.Device;


import com.clx.clxdash.jpa.DeviceEntity;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("device_api/")
public class DeviceController {

    @Autowired
    DeviceRespository deviceRespository;

    //创建一个新的设备
    @RequestMapping("create_device")
    String createDevice(@RequestParam String username,
                        @RequestParam String uuid,
                        @RequestParam String type,
                        @RequestParam String description){

        System.out.println("username = "+username);
        System.out.println("uuid = "+uuid);
        System.out.println("type = "+type);
        System.out.println("description = "+description);

        DeviceEntity deviceEntity = new DeviceEntity();

        deviceEntity.setDescription(description);
        deviceEntity.setType(type);
        deviceEntity.setUuid(uuid);

        try {
            deviceRespository.save(deviceEntity);

        }catch (Exception e){

            return "failed";
        }

        return "success";
    }



    //删除一个设备
    @RequestMapping("delete_device")
    String deleteDevice(){

        return "success";
    }


    @RequestMapping("getdevicelistbyusername")
    String getdevicelistbyusername(@RequestParam String username){


        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setUsername(username);


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉


        Example<DeviceEntity> example = Example.of(deviceEntity,matcher);
//        messageEntity
        List<DeviceEntity> devicelist = deviceRespository.findAll(example);
//        String role = "";

//        if (user.size() == 1){
//            role = user.get(0).getLevel();
//        }else{
//
//        }

        JSONObject json = new JSONObject();
        json.put("result",devicelist.size());

        String uuidlist="[";//="";//[] = new String[devicelist.size()];
        String typelist="[";
        String desclist="[";

        for (int i=0;i<devicelist.size();++i){
            //JSONObject jsonsub = new JSONObject();
//            jsonsub.put("uuid",devicelist.get(i).getUuid());
//            jsonsub.put("type",devicelist.get(i).getType());
//            jsonsub.put("description",devicelist.get(i).getDescription());
//
//
//            json.put(String.valueOf(i),jsonsub.toJSONString());

            uuidlist += devicelist.get(i).getUuid() + ", ";
            typelist += devicelist.get(i).getType() + ", ";
            desclist += devicelist.get(i).getDescription() + ", ";
        }

        uuidlist += "]";
        typelist += "]";
        desclist += "]";



        json.put("uuid",uuidlist);
        json.put("type",typelist);
        json.put("description",desclist);

        return json.toJSONString();
    }


//
//    //发布消息
//    @RequestMapping("publish")
//    String publish(@RequestParam String uuid, @RequestParam String topic,@RequestParam String msg){
//
//
//        //插入到数据库中去
//
//        deviceRespository.save();
//
//
//        return "success";
//    }
//







}
