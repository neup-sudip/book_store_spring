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

import com.example.first.user.User;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

public class JwtConfig extends OncePerRequestFilter {

    private static final String secret_key = "jisdhcinsidhciibcudcguisudbcsjbdcsbdcusblcbsvuslhsbludbcsjdbciusdc" +
            "7fcn74efnf47fn78888hcb78438fw2b78w84gfb7gfb78sgb78gbuyb7sgcf7b4" +
            "f73wifbyw7syb4bnc784gfggb78gb7gbw78fgwgfb878wgef78nw3fc87w3fbwgfb" +
            "cwefcbuwc8ow7e7e4fb8787rvbuoes8r7vuyer87vcuy7wsbyervdjvjdbvjbd";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/users/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwtToken = extractToken(request);
            validateToken(jwtToken);

            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());


            response.setStatus(401);

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
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(key)
                .claim("user", user)
                .compact();
    }

    public static Claims decodeToken(String token) {
        try{
            Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException ex){
            throw new CustomException("Token Expired !");
        }catch (Exception ex){
            throw new CustomException("Token Invalid !");
        }

        Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
