package com.store.store.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/hello-secured")
    public String helloSecured() {
        return "Hello world secured";
    }
}
