package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.config.JwtTokenProvider;
import com.controller.AuthController;
import com.exception.UserException;
import com.modal.User;
import com.repository.UserRepository;
import com.request.LoginRequest;
import com.response.AuthResponse;
import com.service.CustomUserDetailsService;

public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUserHandler_SuccessfulRegistration() throws UserException {
        
        User user = new User();
        user.setEmail("user1@example.com");
        user.setPassword("password");
        user.setFull_name("Test User");

       
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

       
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");

       
        when(jwtTokenProvider.generateJwtToken(any())).thenReturn("jwtToken");

      
        ResponseEntity<AuthResponse> response = authController.createUserHandler(user);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus());
        assertNotNull(response.getBody().getJwt());

      
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(passwordEncoder, times(1)).encode(user.getPassword());
      
        verify(jwtTokenProvider, times(1)).generateJwtToken(any());
    }

    @Test
    public void testCreateUserHandler_ExistingEmail() {
       
        User user = new User();
        user.setEmail("existing@example.com");
        user.setPassword("password");
        user.setFull_name("Existing User");

        
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

       
        assertThrows(UserException.class, () -> authController.createUserHandler(user));

       
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void testSignin_SuccessfulSignIn() {
       
        String email = "user1@example.com";
        String password = "password";
        UserDetails userDetails = mock(UserDetails.class);

        
        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

       
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(true);

        
        when(jwtTokenProvider.generateJwtToken(any())).thenReturn("jwtToken");

       
        ResponseEntity<AuthResponse> response = authController.signin(new LoginRequest(email, password));

      
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus());
        assertNotNull(response.getBody().getJwt());

        
        verify(customUserDetailsService, times(1)).loadUserByUsername(email);
        verify(passwordEncoder, times(1)).matches(password, userDetails.getPassword());
        verify(jwtTokenProvider, times(1)).generateJwtToken(any());
    }

    @Test
    public void testSignin_InvalidCredentials() {
       
        String email = "user1@example.com";
        String password = "wrong_password";
        UserDetails userDetails = mock(UserDetails.class);

       
        when(customUserDetailsService.loadUserByUsername(email)).thenReturn(userDetails);

       
        when(passwordEncoder.matches(password, userDetails.getPassword())).thenReturn(false);

        
        assertThrows(BadCredentialsException.class, () -> authController.signin(new LoginRequest(email, password)));

        // Verify that customUserDetailsService.loadUserByUsername and passwordEncoder.matches were called
        verify(customUserDetailsService, times(1)).loadUserByUsername(email);
        verify(passwordEncoder, times(1)).matches(password, userDetails.getPassword());
    }
}
