package com.clx.clxdash.uuid;

import java.util.UUID;

public class Uuid {





    public static boolean isValidUUID(String uuid) {
//        // UUID校验
//        if (uuid == null) {
//            System.out.println("uuid is null");
//        }
//        String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
//        if (uuid.matches(regex)) {
//            return true;
//        }
//        return false;

        System.out.println(uuid);
        try {
            UUID.fromString(uuid).toString();
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }
//---------------------
//    作者：kangyucheng
//    来源：CSDN
//    原文：https://blog.csdn.net/Kangyucheng/article/details/86498341
//    版权声明：本文为博主原创文章，转载请附上博文链接！
}
