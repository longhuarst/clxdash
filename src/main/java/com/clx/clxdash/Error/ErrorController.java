package com.clx.clxdash.Error;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error/")
public class ErrorController {



    @RequestMapping("notLogin")
    String notLogin(){
        return "需要先登陆!";
    }


    @RequestMapping("notRole")
    String notRole(){
        return "没有权限访问!";
    }



}
