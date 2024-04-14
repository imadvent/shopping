package com.shopping.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Claims extractAllClaims(String token);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);

    boolean validateToken(String token, UserDetails userDetails);

    String generateToken(String username);

    String extractUsername(String token);

    String createToken(Map<String, Object> claims, String username);

    Key getSignKey();
}
