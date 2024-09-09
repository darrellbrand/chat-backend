package com.djf.chat_boss.controller;

import com.djf.chat_boss.chat.RegisterResponse;
import com.djf.chat_boss.security.ApiKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    public AuthController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    private final ApiKeyService apiKeyService;

    @GetMapping("/token")
    public ResponseEntity<RegisterResponse> getToken(@RequestParam String email) {
        RegisterResponse response = new RegisterResponse();
        response.setToken(apiKeyService.getToken(email));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
