package com.example.first.utils;

//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.SignatureException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JwtConfig extends OncePerRequestFilter {
//    private static final String secretKey = "key";
//    private static final long expirationTime = 864_000_000;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if(token == null){
//            System.out.println("no token");
////            throw new CustomException("No token");
//            filterChain.doFilter(request, response);
//        }
//
//        System.out.println("hello");
//        try {
//            if ( !(Jwts.parser().setSigningKey(secretKey).build().parseEncryptedClaims(token).getPayload() == null)) {
//                filterChain.doFilter(request, response);
//            }
//        } catch (JwtException e) {
//            System.out.println("token error");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Unauthorized");
//        }
//    }
//
//    // Example JWT generation
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//
//    // Example JWT validation
//    public boolean validateToken(String token) {
//       return  false;
//    }
//
//}
//

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = extractToken(request);
            validateToken(jwtToken);

            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            System.out.println("Error " + e.getMessage());
            throw new CustomException("Error in token");
        }
    }

    private String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private void validateToken(String token) {
        if (token == null || !isValid(token)) {
            throw new CustomException("Invalid or missing token");
        }
    }

    private boolean isValid(String token) {
        return true;
    }
}
