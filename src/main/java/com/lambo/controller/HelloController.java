package com.lambo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by allen on 18-3-27.
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String hello(){
        return "hello world";
    }
}
