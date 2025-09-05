package top.chsh.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Value("${my.feature.helloSwitch}")
    private boolean isHelloEnabled;

    @Value("${my.feature.closeMsg}")
    private String closeMessage;

    @GetMapping("/feat")
    public String hello(){
        if(isHelloEnabled){
            return "接口开放中，欢迎访问我们的第一个Spring Boot项目";
        } else {
            return closeMessage;
        }
    }
}
