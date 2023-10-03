package com.service;

import java.util.List;

import com.dto.UserDto;
import com.exception.UserException;
import com.modal.User;
import com.request.UpdateUserRequest;

public interface UserService {
	
	public User findUserProfile(String jwt);
	
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
	
	public User findUserById(Integer userId) throws UserException;
	
	public List<User> searchUser(String query);
	
	public void deleteUserById(Integer userId) throws UserException;
}
