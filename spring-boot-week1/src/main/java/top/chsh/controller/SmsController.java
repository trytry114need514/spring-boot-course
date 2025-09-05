package top.chsh.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.chsh.service.SmsService;

@RestController
public class SmsController {
    @Resource
    private SmsService SmsService;
    @Autowired
    private SmsService smsService;

    @GetMapping("/sms")
    public void sendSms(String phone){
        smsService.sendSms(phone);
    }
}
