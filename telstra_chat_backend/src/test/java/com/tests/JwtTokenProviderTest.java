package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.config.JwtTokenProvider;
import com.config.SecurityConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private SecretKey key;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes());
    }

    @Test
    public void testGenerateJwtToken() {
        
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");

       
        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);

        
        Map<String, Object> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

        
        assertEquals("Team5", ((Claims) claims).getIssuer());
        assertEquals("test@example.com", claims.get("email"));
        
    }

    @Test
    public void testGetEmailFromToken() {
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "test@example.com");
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer("Team5")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .signWith(key)
                .compact();

        
        String email = jwtTokenProvider.getEmailFromToken("Bearer " + jwtToken);

        
        assertEquals("test@example.com", email);
    }
}
