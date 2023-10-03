package com.service;

import java.util.List;

import com.exception.ChatException;
import com.exception.MessageException;
import com.exception.UserException;
import com.modal.Message;
import com.request.SendMessageRequest;

public interface MessageService  {
	
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;
	
	public List<Message> getChatsMessages(Integer chatId) throws ChatException;
	
	public Message findMessageById(Integer messageId) throws MessageException;
	
	public String deleteMessage(Integer messageId) throws MessageException;

}
