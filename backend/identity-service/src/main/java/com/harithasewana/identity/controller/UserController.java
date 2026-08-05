package com.harithasewana.identity.controller;

import com.harithasewana.identity.entity.User;
import com.harithasewana.identity.service.UserService;
import com.harithasewana.identity.service.TestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/identity")
public class UserController {

    private final UserService userlocal;
    private final TestService test;

    public UserController(UserService userService, TestService testService){
        this.userlocal = userService;
        this.test = testService;
    }

    @GetMapping("/hello")
    public String hello(){
        return userlocal.getMessage();
    }

    @GetMapping("/test")
    public String test(){
        return test.test();
    }

    @GetMapping("/testdb")
    public String testdb(){
        //hello.createUser();
        return "Insert to DB";
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user)
    {
        return userlocal.createUser(user);
    }
}
