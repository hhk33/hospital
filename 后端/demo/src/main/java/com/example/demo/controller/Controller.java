package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.JwtUtil;
import com.example.demo.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String Hello() {
        System.out.println("demo1");
        User user = new User();
        user.setName("user1");
        user.setPassword("123");
        return userMapper.selectID(user);
    }

    //用户登录
    @PostMapping("api/userLogin")
    public Result userLogin(@RequestBody Map<String, String> map) {
        System.out.println("demo2");
        String account = map.get("account");
        String password = map.get("password");
        //数据库搜索账号
        User user = new User();
        user.setName(account);
        user.setPassword(password);
        String id = userMapper.selectID(user);
        if (!id.equals("")) {
            //生成Token
            String accessToken = JwtUtil.createToken(id, "acc");
            String refreshToken = JwtUtil.createToken(id, "ref");
            return Result.success().data("accessToken", accessToken)
                    .data("refreshToken", refreshToken);
        }
        return Result.error();
    }

    //获取用户信息
    @GetMapping("api/userInfo/{accessToken}/{refreshToken}")
    public Result userIngo(@PathVariable("accessToken") String accessToken,
                           @PathVariable("refreshToken") String refreshToken) {
        String accResult = JwtUtil.checkToken(accessToken);
        String refResult = JwtUtil.checkToken(refreshToken);
        User user = new User();
        String name = null;

        if (accResult.equals("error") || refResult.equals("error"))
            return Result.error();
        //两token均过期
        else if (accResult.equals("tokenExpired") && refResult.equals("tokenExpired"))
            return Result.overdue();
        //将过期
        else if (accResult.equals("tokenExpired")) {
            user.setId(refResult);
            name = userMapper.selectName(user);

            Result result = Result.success().data("username",name);
            result.setCode(201);
            //生成新Token
            String newAccessToken = JwtUtil.createToken(refResult, "acc");
            String newRefreshToken = JwtUtil.createToken(refResult, "ref");
            result.data("accessToken", newAccessToken).data("refreshToken", newRefreshToken);
            return result;
        }
        //未过期
        user.setId(refResult);
        name = userMapper.selectName(user);
        return Result.success().data("username", name);
    }

}