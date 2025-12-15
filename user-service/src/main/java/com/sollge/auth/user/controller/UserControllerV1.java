package com.sollge.auth.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sollge.auth.token.JsonTokenService;
import com.sollge.auth.user.dto.auth.AuthRequestDTO;
import com.sollge.auth.user.dto.auth.AuthResponseDTO;
import com.sollge.auth.user.dto.auth.TokenValidatedResponseDTO;
import com.sollge.auth.user.dto.registration.RegistrationRequestDTO;
import com.sollge.auth.user.dto.user.UserDTO;
import com.sollge.auth.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserControllerV1 {

    private final JsonTokenService tokenService;
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@RequestParam("id") Long id) {
        return ResponseEntity.of(userService.find(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserProfile(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isPresentById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.find(id).isPresent());
    }

    @GetMapping(value = "/exists/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isPresentByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email).isPresent());
    }

    @GetMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenValidatedResponseDTO> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return ResponseEntity.ok(tokenService.validateToken(authorizationHeader));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegistrationRequestDTO registrationDTO) {
        return ResponseEntity.ok(userService.register(registrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
