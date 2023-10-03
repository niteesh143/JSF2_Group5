package com.tests;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.controller.ChatController;
import com.exception.UserException;
import com.modal.Chat;
import com.modal.User;
import com.request.SingleChatRequest;
import com.service.ChatService;
import com.service.UserService;

public class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateSingleChatHandler() throws UserException {
        // Mock data
        String jwt = "mocked-jwt-token";
        User reqUser = new User();
        SingleChatRequest request = new SingleChatRequest();
        Chat chat = new Chat();

        // Mock userService behavior
        when(userService.findUserProfile(jwt)).thenReturn(reqUser);

        // Mock chatService behavior
        when(chatService.createChat(reqUser.getId(), request.getUserId(), false)).thenReturn(chat);

        // Perform the test
       // ResponseEntity<ChatDto> response = chatController.creatChatHandler(request, jwt);

        // Verify the result
       //assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify that userService.findUserProfile and chatService.createChat were called
        //verify(userService, times(1)).findUserProfile(jwt);
        //verify(chatService, times(1)).createChat(reqUser.getId(), request.getUserId(), true);
    }

    // Similarly, you can write test cases for other methods in the ChatController class.
}
