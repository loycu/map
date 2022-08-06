package com.example.eurekaprovider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
    
    @GetMapping("/")
    public String callProvider() throws Exception {
        return "account provider OK";
    }
    
}
