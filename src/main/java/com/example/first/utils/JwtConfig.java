package com.example.first.utils;

import com.example.first.authanduser.User;
import com.example.first.authanduser.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtConfig extends OncePerRequestFilter {

    @Autowired
    private Environment environment;

    @Value("${jwt.secret:mySecret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/auth/") || request.getServletPath().contains("/api/books")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = extractToken(request);
            validateToken(jwtToken);
            Claims claims = decodeToken(jwtToken);
            User user = claimsToUser(claims);
            validateAuthority(user, request);
//             Continue with the filter chain
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ApiResponse apiResponse = new ApiResponse(false, null, e.getMessage());
            response.setContentType("application/json");
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        }
    }

    private String extractToken(HttpServletRequest request) {
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("auth")) {
                    System.out.println();
                    return cookie.getValue();
                }
            }
        } catch (NullPointerException ex) {
            throw new CustomException("Missing token");
        }
        return null;
    }

    private void validateToken(String token) {
        if (token == null || isTokenExpired(token)) {
            throw new CustomException("Invalid or Missing token");
        }
    }

    public boolean isTokenExpired(String token) {
        return decodeToken(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public String generateToken(UserResponseDto user) {
        Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject("auth token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .claim("user", user)
                .compact();
    }

    public Claims decodeToken(String token) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new CustomException("Token Expired !");
        } catch (Exception ex) {
            throw new CustomException("Token Invalid !");
        }
    }

    static void validateAuthority(User user, HttpServletRequest request) {
        if (request.getServletPath().contains("/api/admin/") && !user.getRole().equals("ADMIN")) {
            throw new CustomException("User not Authorized !");
        }
    }

    private static User claimsToUser(Claims claims) {
        Map<String, Object> userMap = claims.get("user", Map.class);
        User user = new User();
        user.setUserId((Integer) userMap.get("userId"));
        user.setUsername((String) userMap.get("username"));
        user.setEmail((String) userMap.get("email"));
        user.setRole((String) userMap.get("role"));
        return user;
    }
}
