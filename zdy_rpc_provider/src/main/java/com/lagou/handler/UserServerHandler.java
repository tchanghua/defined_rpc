package com.lagou.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lagou.service.UserService;
import com.lagou.service.UserServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;


public class UserServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg != null) {
            System.out.println(msg.toString());
            String s = msg.toString();
            String msgStr = s.substring(s.indexOf("{"));
            System.out.println(msgStr);
            JSONObject jsonObject = JSON.parseObject(msgStr);
            String className = jsonObject.get("className").toString()+"Impl";
            String methodName = jsonObject.get("methodName").toString();
            JSONArray parameters =(JSONArray) jsonObject.get("parameters");
            String param = parameters.get(0).toString();
            System.out.println(parameters);
            Object parameterTypes = jsonObject.get("parameterTypes");
            Class<?> aClass = Class.forName(className);
            UserService userService =(UserService) aClass.newInstance();
            Method declaredMethod = aClass.getDeclaredMethod(methodName,String.class);
            String res = (String)declaredMethod.invoke(userService, param);
            ctx.writeAndFlush(res);
            // 判断是否符合约定，符合则调用本地方法，返回数据
            // msg:  UserService#sayHello#are you ok?
//            if (msg.toString().startsWith("UserService")) {
//                UserServiceImpl userService = new UserServiceImpl();
//                String result = userService.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
//                ctx.writeAndFlush(result);
//            }
        }


    }
}
