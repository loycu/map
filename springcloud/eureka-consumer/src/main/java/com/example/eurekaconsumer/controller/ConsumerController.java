package com.example.eurekaconsumer.controller;

import com.example.eurekaconsumer.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2020-10-11 23:45
 **/
@RestController
public class ConsumerController {

    @RequestMapping("/")
    public String health() throws Exception {
        return "consumer OK";
    }

    @Autowired
    private ProviderClient providerClient;

    @RequestMapping("/consumer/callProvider")
    public String callProvider() {
        //使用Feign客户端调用其他服务的接口
        return providerClient.list();
    }

    @RequestMapping("/consumer/callProvider/timeOut")
    public String timeOut() {
        //使用Feign客户端调用其他服务的接口
        return providerClient.callProviderTimeOut();
    }


}
