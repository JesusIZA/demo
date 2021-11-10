package com.example.demo.rest;

import com.example.demo.dto.UserModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/register")
    public String register(@RequestBody UserModel userModel) {
        return userModel.toString();
    }

    @GetMapping("/user")
    public String register() {
        return "User!";
    }

}
