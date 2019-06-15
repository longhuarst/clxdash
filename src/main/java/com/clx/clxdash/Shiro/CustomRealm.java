//package com.clx.clxdash.Shiro;
//
//import com.clx.clxdash.User.UserRepository;
////import com.clx.clxdash.jpa.MessageEntity;
//import com.clx.clxdash.jpa.UserEntity;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class CustomRealm extends AuthorizingRealm {
//
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    //获取身份验证信息
//    //Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//
//        System.out.println("————身份认证方法————");
//
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        // 从数据库获取对应用户名密码的用户
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(token.getUsername());
//
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉
//
//
//        Example<UserEntity> example = Example.of(userEntity,matcher);
////        messageEntity
//        List<UserEntity> user = userRepository.findAll(example);
//
//        String password;
//
//        if (user.size() == 1){
//            password = user.get(0).getPassword();
//            if (!password.equals(new String((char[]) token.getCredentials()))){
//                throw new AccountException("密码不正确");
//            }
//        }else {
//            throw new AccountException("用户名不正确");
//        }
//
//
//        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
//
//
//
//
//
//
//
////        String password = userMapper.getPassword(token.getUsername());
////        if (null == password) {
////            throw new AccountException("用户名不正确");
////        } else if (!password.equals(new String((char[]) token.getCredentials()))) {
////            throw new AccountException("密码不正确");
////        }
////        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
////        ---------------------
////                作者：Howie_Y
////        来源：CSDN
////        原文：https://blog.csdn.net/weixin_38132621/article/details/80216056
////        版权声明：本文为博主原创文章，转载请附上博文链接！
//        //return null;
//    }
//
//
//    //获取授权信息
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//
//        System.out.println("————权限认证————");
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //获得该用户角色
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(username);
//
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
//                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉
//
//
//        Example<UserEntity> example = Example.of(userEntity,matcher);
////        messageEntity
//        List<UserEntity> user = userRepository.findAll(example);
//        String role = "";
//
//        if (user.size() == 1){
//            role = user.get(0).getLevel();
//        }else{
//
//        }
//
//        Set<String> set = new HashSet<>();
//        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
//        set.add(role);
//        //设置该用户拥有的角色
//        info.setRoles(set);
//        return info;
////        ---------------------
////                作者：Howie_Y
////        来源：CSDN
////        原文：https://blog.csdn.net/weixin_38132621/article/details/80216056
////        版权声明：本文为博主原创文章，转载请附上博文链接！
//        //return null;
//    }
//
//
//}
package com.clx.clxdash.Shiro;

import com.clx.clxdash.User.UserRepository;
//import com.clx.clxdash.jpa.MessageEntity;
import com.clx.clxdash.jpa.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {


    @Autowired
    UserRepository userRepository;


    //获取身份验证信息
    //Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("————身份认证方法————");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(token.getUsername());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withIgnorePaths("id");  //忽略属性：是否关注。因为是基本类型，需要忽略掉


        Example<UserEntity> example = Example.of(userEntity,matcher);
//        messageEntity
        List<UserEntity> user = userRepository.findAll(example);

        String password;

        if (user.size() == 1){
            password = user.get(0).getPassword();
            if (!password.equals(new String((char[]) token.getCredentials()))){
                throw new AccountException("密码不正确");
            }
        }else {
            throw new AccountException("用户名不正确");
        }


        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());







//        String password = userMapper.getPassword(token.getUsername());
//        if (null == password) {
//            throw new AccountException("用户名不正确");
//        } else if (!password.equals(new String((char[]) token.getCredentials()))) {
//            throw new AccountException("密码不正确");
//        }
//        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
//        ---------------------
//                作者：Howie_Y
//        来源：CSDN
//        原文：https://blog.csdn.net/weixin_38132621/article/details/80216056
//        版权声明：本文为博主原创文章，转载请附上博文链接！
        //return null;
    }


    //获取授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色

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

        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
//        ---------------------
//                作者：Howie_Y
//        来源：CSDN
//        原文：https://blog.csdn.net/weixin_38132621/article/details/80216056
//        版权声明：本文为博主原创文章，转载请附上博文链接！
        //return null;
    }


}
