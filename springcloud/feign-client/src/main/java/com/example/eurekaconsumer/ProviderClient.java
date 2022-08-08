package com.example.eurekaconsumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;
//@FeignClient(name = "account",url = "http://localhost:8083/account") //TODO 將來去掉url
@FeignClient(name = "account") //TODO 將來去掉url
//会扫描指定包下，标记FeignClient注解的接口
//会根据服务名，从注册中心找到对应的IP地址
public interface ProviderClient {
    //这里跟提供者接口的URL一致
    @GetMapping(value = "/")
    String list();

    @GetMapping("/timeout")
    String callProviderTimeOut() ;

}