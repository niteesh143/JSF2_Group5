package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controller.UserController;
import com.dto.UserDto;
import com.exception.UserException;
import com.modal.User;
import com.request.UpdateUserRequest;
import com.service.UserService;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateUserHandler() throws UserException {
        
        UpdateUserRequest request = new UpdateUserRequest();
        int userId = 1;
        User updatedUser = new User(); 

        
        when(userService.updateUser(userId, request)).thenReturn(updatedUser);

        
        ResponseEntity<UserDto> response = userController.updateUserHandler(request, userId);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser.getEmail(), response.getBody().getEmail());

        
        verify(userService, times(1)).updateUser(userId, request);
    }

    @Test
    public void testGetUserProfileHandler() {
        
        String jwt = "mocked-jwt-token";
        User user = new User(); 

        
        when(userService.findUserProfile(jwt)).thenReturn(user);

        
        ResponseEntity<UserDto> response = userController.getUserProfileHandler(jwt);

        
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(user.getEmail(), response.getBody().getEmail());

        
        verify(userService, times(1)).findUserProfile(jwt);
    }

    @Test
    public void testSearchUsersByName() {
        
        String name = "John";
        User user = new User(); 
        when(userService.searchUser(name)).thenReturn(Collections.singletonList(user));

       
        ResponseEntity<HashSet<UserDto>> response = userController.searchUsersByName(name);

        
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(1, response.getBody().size()); 

        
        verify(userService, times(1)).searchUser(name);
    }
}

