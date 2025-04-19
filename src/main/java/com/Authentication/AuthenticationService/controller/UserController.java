package com.Authentication.AuthenticationService.controller;

import com.Authentication.AuthenticationService.model.Users;
import com.Authentication.AuthenticationService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }

    @PostMapping("/signing")
    public String login(@RequestBody Users user) {
        return userService.verify(user);
    }
}
