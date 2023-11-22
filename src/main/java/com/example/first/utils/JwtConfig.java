package com.example.first.utils;

import com.example.first.user.User;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtConfig extends OncePerRequestFilter {

    private static final String secret_key = "jisdhcinsidhciibcudcguisudbcsjbdcsbdcusblcbsvuslhsbludbcsjdbciusdc" +
            "7fcn74efnf47fn78888hcb78438fw2b78w84gfb7gfb78sgb78gbuyb7sgcf7b4" +
            "f73wifbyw7syb4bnc784gfggb78gb7gbw78fgwgfb878wgef78nw3fc87w3fbwgfb" +
            "cwefcbuwc8ow7e7e4fb8787rvbuoes8r7vuyer87vcuy7wsbyervdjvjdbvjbd";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/users/login") || request.getServletPath().contains("/api/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = extractToken(request);
            validateToken(jwtToken);
            Claims claims = decodeToken(jwtToken);
            User user = claimsToUser(claims);
            validateAuthority(user, request);

            // Continue with the filter chain
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            System.out.println("Custom Error: " + e.getMessage());
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
            System.out.println(request.getHeader("Auth"));
            throw new CustomException("Invalid or Missing token");
        }
        return null;
    }

    private void validateToken(String token) {

        if (token == null || isTokenExpired(token)) {
            throw new CustomException("Invalid or missing token");
        }
    }

    public boolean isTokenExpired(String token) {
        return decodeToken(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public String generateToken(User user) {
        Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject("auth token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .claim("user", user)
                .compact();
    }

    static Claims decodeToken(String token) {
        try {
            Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new CustomException("Token Expired !");
        } catch (Exception ex) {
            throw new CustomException("Token Invalid !");
        }

        Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    static void validateAuthority(User user, HttpServletRequest request) {
        if (request.getServletPath().contains("/api/admin/") && !user.getRole().equals("ADMIN")) {
            throw new CustomException("User not authorized !");
        }
    }

    public static User claimsToUser(Claims claims) {
        Map<String, Object> userMap = claims.get("user", Map.class);
        User user = new User();
        user.setUserId((Integer) userMap.get("userId"));
        user.setUsername((String) userMap.get("username"));
        user.setEmail((String) userMap.get("email"));
        user.setRole((String) userMap.get("role"));
        return user;
    }
}
