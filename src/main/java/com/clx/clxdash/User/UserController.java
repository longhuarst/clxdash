package com.clx.clxdash.User;


import com.clx.clxdash.jpa.UserEntity;

import com.clx.clxdash.jpa.UserLogin;
import net.minidev.json.JSONObject;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user_api/")
public class UserController {


    @Autowired
    UserRepository userRepository;




    @RequestMapping("register")
    public String register(@RequestParam("data") String request){





//        System.out.println(request.getUsername());
//        System.out.priintln(request.getUsername());
//        System.out.println(request.getPassword());

        System.out.println(request.toString());

//        UserEntity user = new UserEntity();
//
//        user.setName("陆慧晨");
//
//
//        try {
//
//            userRepository.save(user);
//        }catch (){
//
//        }

        List<UserEntity> list = userRepository.findAll();

        UserEntity user2 = new UserEntity();

        user2.setName("xx");
        user2.setAddress("");
        user2.setEmail("x");

        Timestamp ts = new Timestamp(new Date().getTime());
        user2.setLastlogintime(ts);
        user2.setRegistertime(ts);
        user2.setLevel("1");
        user2.setPassword("xx");
        user2.setPhone("");
        user2.setUsername("xx");


        try {

            userRepository.save(user2);
        }catch (Exception exception){


            System.out.println(exception.getCause());
            System.out.println(exception.getLocalizedMessage());

            System.out.println(exception.getMessage());

            System.out.println(exception.getStackTrace());


            System.out.println(exception.getSuppressed());


            //注册失败
            return "register_error";


//            System.out.println(exception.);
        }


        //注册成功
        return "register_success";

//        userRepository.flush();
//
//
//        if (list.size() > 0) {
//            modelMap.put("users", list.get(0));
//            System.out.println("-----");
//        }
//
//
//        System.out.println("xx");
//
//        return "xx";

    }



    @ResponseBody
    @RequestMapping("login")
    public String login(@RequestParam String username, @RequestParam String password){
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        subject.login(token);
        //根据权限，指定返回数据
        //String role = userMapper.getRole(username);


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉


        Example<UserEntity> example = Example.of(userEntity,matcher);
//        messageEntity
        List<UserEntity> user = userRepository.findAll(example);
        String role = "";

        if (user.size() == 1){
            role = user.get(0).getLevel();
        }else{

        }

        JSONObject json = new JSONObject();


        System.out.println("role="+role);

        if ("user".equals(role)) {
            //return resultMap.success().message("欢迎登陆");
            json.put("result","success");
        } else if ("admin".equals(role)) {
            //return resultMap.success().message("欢迎来到管理员页面");
            json.put("result","success");
        }else{
            json.put("result","failed");
        }

        return json.toJSONString();
        //return resultMap.fail().message("权限错误！");
//        ---------------------
//                作者：Howie_Y
//        来源：CSDN
//        原文：https://blog.csdn.net/weixin_38132621/article/details/80216056
//        版权声明：本文为博主原创文章文为博主原创文章，转载请附上博文链接！

//
//
//        JSONObject json = new JSONObject();
//
//        System.out.println("login");
//        System.out.println("username = "+username);
//        System.out.println("password = "+password);
//
//        if (username.equals("") || password.equals("")){
//            json.put("result","failed");
//            return json.toJSONString();//"failed";
//        }
//
//        //查询数据库
//        List<UserEntity> userlist = userRepository.findAll();
//
//        for (UserEntity user: userlist) {
//            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
//                json.put("result","success");
//                return json.toJSONString();//"success";
//            }
//        }
//
//
//
//
//
//
//
//        json.put("result","failed");
//        return json.toJSONString();//"failed";
    }








    @RequestMapping("logout")
    String logout(){
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();

        System.out.println("成功注销");
        return "logout";
    }




}
