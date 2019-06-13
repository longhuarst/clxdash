package com.clx.clxdash.Netty.Table;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListenTable {



    private static class ListenTableHolder {
        private static ListenTable instance = new ListenTable();
    }

    private ListenTable() {
        UuidContextMap = new HashMap<String, Set<ChannelHandlerContext>>();
        ContextUuidMap = new HashMap<ChannelHandlerContext, String>();
        // System.out.println("构造");
    }

    public static ListenTable getInstance() {
        return ListenTableHolder.instance;
    }

    // =======================================



    Map<String, Set<ChannelHandlerContext>> UuidContextMap;
    Map<ChannelHandlerContext, String> ContextUuidMap;





    // 订阅者删除
    public void remove(ChannelHandlerContext ctx) {
        String uuid = ContextUuidMap.get(ctx);// 获取话题
        ContextUuidMap.remove(ctx);

        if (uuid != null) {
            // 有话题订阅的
            Set<ChannelHandlerContext> set = UuidContextMap.get(uuid);
            set.remove(ctx);

            // 检测是不是空了

            if (set.isEmpty()) {
                UuidContextMap.remove(uuid);// 没有订阅者了，把话题删除
            } else {
                UuidContextMap.put(uuid, set);
            }

        }
    }




    // 订阅者加入
    public void add(ChannelHandlerContext ctx, String uuid) {
        Set<ChannelHandlerContext> set = UuidContextMap.get(uuid);
        if (set == null) {
            set = new HashSet<ChannelHandlerContext>();
        }
        set.add(ctx);
        UuidContextMap.put(uuid, set);

        ContextUuidMap.put(ctx, uuid);
    }




    // 获取转发对象

    public Set<ChannelHandlerContext> get(String uuid) {
        return UuidContextMap.get(uuid);
    }



    // 列表
    public String uuidList() {
        String data = "";

        data += "{\r\n";

        for (String key : UuidContextMap.keySet()) {
            data += "\t" + key + " : { ";
            for (ChannelHandlerContext ctx : UuidContextMap.get(key)) {
                data += ctx.channel().remoteAddress().toString() + ", ";
            }
            data += "},\r\n";
        }

        data += "};";

        return data;
    }



    // 列表
    public String contextlist() {
        String data = "";

        data += "{\r\n";

        for (ChannelHandlerContext ctx : ContextUuidMap.keySet()) {
            data += "\t" + ctx.channel().remoteAddress().toString() + " : { ";
            data += ContextUuidMap.get(ctx) + ", ";
            data += "},\r\n";
        }

        data += "};";

        return data;
    }

}
