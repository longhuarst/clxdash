package com.clx.clxdash.User;


import com.clx.clxdash.jpa.UserEntity;

import com.clx.clxdash.jpa.UserLogin;
import net.minidev.json.JSONObject;
import org.apache.catalina.User;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
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




        JSONObject json = new JSONObject();

        System.out.println("login");
        System.out.println("username = "+username);
        System.out.println("password = "+password);

        if (username.equals("") || password.equals("")){
            json.put("result","failed");
            return json.toJSONString();//"failed";
        }

        //查询数据库
        List<UserEntity> userlist = userRepository.findAll();

        for (UserEntity user: userlist) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                json.put("result","success");
                return json.toJSONString();//"success";
            }
        }







        json.put("result","failed");
        return json.toJSONString();//"failed";
    }





}
