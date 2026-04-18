package com.taskforge.taskforge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "APP WORKING";
    }

    @GetMapping("/test")
    public String test() {
        return "GLOBAL TEST WORKING";
    }
}