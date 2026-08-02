package com.harithasewana.identity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/identity")
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Identity Service is running";
    }
}
