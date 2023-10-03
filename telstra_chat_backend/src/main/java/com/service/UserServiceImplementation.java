package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.config.JwtTokenProvider;
import com.dto.UserDto;
import com.exception.UserException;
import com.modal.User;
import com.repository.UserRepository;
import com.request.UpdateUserRequest;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
		
		System.out.println("update find user ------- ");
		User user=findUserById(userId);
		
		System.out.println("update find user ------- "+user);
		
		if(req.getFull_name()!=null) {
			user.setFull_name(req.getFull_name());
		}
		if(req.getProfile_picture()!=null) {
			user.setProfile_picture(req.getProfile_picture());
		}
		
		return userRepo.save(user);
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> opt=userRepo.findById(userId);
		
		if(opt.isPresent()) {
			User user=opt.get();
			
			return user;
		}
		throw new UserException("user not exist with id "+userId);
	}

	@Override
	public User findUserProfile(String jwt) {
		String email = jwtTokenProvider.getEmailFromToken(jwt);
		
		Optional<User> opt=userRepo.findByEmail(email);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		
		throw new BadCredentialsException("recive invalid token");
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepo.searchUsers(query);
		
	}
	
	@Override
	public void deleteUserById(Integer userId) throws UserException {
		Optional<User> userOptional=userRepo.findById(userId);
		
		if(userOptional.isPresent()) {
			User user=userOptional.get();
			userRepo.delete(user);
		}
		else {
			throw new UserException("User not found");
		}
		
	}

}
