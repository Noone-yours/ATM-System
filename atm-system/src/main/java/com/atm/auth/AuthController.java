package com.atm.auth;

import com.atm.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        boolean success = authService.verifyPin(request.getAccountNumber(), request.getPin());
        if (success) {
            return ResponseEntity.ok("Login Successful");
        }
        return ResponseEntity.status(401).body("Invalid Account or PIN");
    }
}
