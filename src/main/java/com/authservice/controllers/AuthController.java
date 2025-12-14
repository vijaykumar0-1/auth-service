package com.authservice.controllers;

import com.authservice.dto.LoginRequest;
import com.authservice.entity.RefreshToken;
import com.authservice.security.JwtTokenProvider;
import com.authservice.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.authservice.common.AuthMessages.INVALID_REFRESH_TOKEN;
import static com.authservice.common.AuthMessages.LOGIN_SUCCESS;
import static com.authservice.common.AuthMessages.LOGOUT_SUCCESS;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        LOGGER.info("Login attempt for username={}", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        LOGGER.info("Login successful for username={}", userDetails.getUsername());
        return ResponseEntity.ok(
                Map.of(
                        "message", LOGIN_SUCCESS,
                        "token", token,
                        "refreshToken", refreshToken.getToken()
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {

        String requestRefreshToken = request.get("refreshToken");

        RefreshToken refreshToken = refreshTokenService
                .findByToken(requestRefreshToken)
                .orElseThrow(() -> new RuntimeException(INVALID_REFRESH_TOKEN));

        refreshTokenService.verifyExpiration(refreshToken);

        String newAccessToken = jwtTokenProvider.generateToken(refreshToken.getUserId());

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        String username = jwtTokenProvider.extractUsername(token);

        refreshTokenService.deleteByUserId(username);
        LOGGER.info("Logout successful for username={}", username);
        return ResponseEntity.ok(Map.of("message", LOGOUT_SUCCESS));
    }

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("This is a protected endpoint!");
    }
}