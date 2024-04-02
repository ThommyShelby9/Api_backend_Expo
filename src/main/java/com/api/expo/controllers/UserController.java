package com.api.expo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import com.api.expo.models.AuthResponse;
import com.api.expo.models.User;
import com.api.expo.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @SuppressWarnings("deprecation")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> inscription(@RequestBody User user) {
        User result = this.userService.register(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/activate")
    public User activation(@RequestBody Map<String, String> activation) {
        return this.userService.activation(activation);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<AuthResponse> connection(@RequestBody User user) {
        AuthResponse authResponse = this.userService.connection(user);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt-token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("Vous avez été déconnecté avec succès.");
    }

    @PostMapping("/password_forgot")
    public User passwordForgot(@RequestBody User user){
        return this.userService.forgotPassword(user);
    }

}
