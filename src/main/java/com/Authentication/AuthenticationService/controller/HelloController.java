package com.Authentication.AuthenticationService.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String Greeting(HttpServletRequest request) {

        return "Welcome to spring Auth!!" + request.getSession().getId();
    }
}
