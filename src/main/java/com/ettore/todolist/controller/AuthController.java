package com.ettore.todolist.controller;

import com.ettore.todolist.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        return authService.generateToken(authentication);
    }
}
