package com.kh.spring_jpa241217.controller;

import com.kh.spring_jpa241217.dto.TokenDto;
import com.kh.spring_jpa241217.dto.request.LoginRequest;
import com.kh.spring_jpa241217.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<TokenDto> handleLogin(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

}
