package com.clx.clxdash.Device;


import com.clx.clxdash.jpa.DeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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




}
