package com.nevtan.authservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private static final String SECRET = "unit-test-secret-key-that-is-definitely-long-enough-256-bits!!";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET, 3600000L);
    }

    @Test
    void generateToken_thenExtractUsername_returnsSameUsername() {
        String token = jwtUtil.generateToken("alice");
        assertNotNull(token);
        assertEquals("alice", jwtUtil.extractUsername(token));
    }

    @Test
    void isTokenValid_withMatchingUsername_returnsTrue() {
        String token = jwtUtil.generateToken("bob");
        assertTrue(jwtUtil.isTokenValid(token, "bob"));
    }

    @Test
    void isTokenValid_withDifferentUsername_returnsFalse() {
        String token = jwtUtil.generateToken("bob");
        assertFalse(jwtUtil.isTokenValid(token, "carol"));
    }

    @Test
    void expiredToken_isNotValid() throws InterruptedException {
        JwtUtil shortLived = new JwtUtil(SECRET, 1L);
        String token = shortLived.generateToken("dave");
        Thread.sleep(10);
        assertFalse(shortLived.isTokenValid(token, "dave"));
    }
}
