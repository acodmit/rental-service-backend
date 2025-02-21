package org.unibl.etf.ip.rentalservice.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.rentalservice.security.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("username"), request.get("password"));
        return Map.of("token", token);
    }
}