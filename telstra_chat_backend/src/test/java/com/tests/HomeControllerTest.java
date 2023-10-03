package com.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.controller.HomeController;

public class HomeControllerTest {

    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        homeController = new HomeController();
    }

    @Test
    public void testHomePageHandler() {
        ResponseEntity<String> response = homeController.homePageHandler();

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("What's App api using spring boot", response.getBody());
    }
}

