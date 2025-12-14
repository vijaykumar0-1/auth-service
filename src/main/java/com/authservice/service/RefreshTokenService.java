package com.authservice.service;

import com.authservice.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String userId);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUserId(String userId);
    Optional<RefreshToken> findByToken(String token);
}
