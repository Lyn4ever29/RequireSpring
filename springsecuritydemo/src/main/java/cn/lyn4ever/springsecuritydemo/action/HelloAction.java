package cn.lyn4ever.springsecuritydemo.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 007
 * @Date: 2019/11/27 13:29
 * @Version: 1.0
 */
@RestController
public class HelloAction {
    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
    @RequestMapping("admin")
    public String admin(){
        return "admin";
    }
    @RequestMapping("user")
    public String user(){
        return "user";
    }


    @RequestMapping("/login")
    public String login() {
        return "请登录!";
    }
    @RequestMapping("/login_error")
    public String loginError() {
        return "登录失败!";
    }
}
