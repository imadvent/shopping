package com.shopping.controller;

import com.shopping.entity.AuthRequest;
import com.shopping.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtControllerTest {

    private static final String USER_NAME = "ADMIN_USER";
    private static final String PASSWORD = "adminpwd";

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtController jwtController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateToken() {

        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserName(USER_NAME);
        authRequest.setPassword(PASSWORD);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        when(jwtService.generateToken(USER_NAME)).thenReturn("generatedToken");

        String token = jwtController.authenticateToken(authRequest);

        assertEquals("generatedToken", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(USER_NAME);

    }

    @Test
    void testAuthenticateToken_InvalidCredentials() {
        // Mocking authRequest
        AuthRequest authRequest = new AuthRequest("username", "password");

        // Mocking authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Testing and verification
        assertThrows(UsernameNotFoundException.class, () -> jwtController.authenticateToken(authRequest));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void testAuthenticateToken_AuthenticationManagerThrowsException() {
        // Mocking authRequest
        AuthRequest authRequest = new AuthRequest("username", "password");

        // Mocking authentication manager to throw an exception
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Authentication failed"));

        // Testing and verification
        assertThrows(RuntimeException.class, () -> jwtController.authenticateToken(authRequest));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(anyString());
    }
}
