package com.imooc.miaosha.controller;


import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.KeyPrefix;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoApplication {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "hello spring";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("right data");
    }

    @RequestMapping("/helloerror")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVICE_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "hello th");
        return "hellot";
    }

    @RequestMapping("/thymeleaf2")
    public String thymeleaf2(Model model) {
        model.addAttribute("name", "hello th2");
        return "hellot";
    }

    //    @Autowired
//    UserService userService;
//    @RequestMapping("/db/get")
//    @ResponseBody
//    public Result<User> dbGet(){
//        User user= userService.getById(0);
//        return Result.success(user);
//    }
    @Autowired
    UserService userService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

    @Autowired
    RedisService redisService;

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> RedisGet() {
        User v1 = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> RedisSet() {
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);

    }

}
