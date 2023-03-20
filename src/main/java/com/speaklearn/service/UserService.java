package com.speaklearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speaklearn.domain.User;
import com.speaklearn.exception.ResourceNotFoundException;
import com.speaklearn.exception.message.ErrorMessage;
import com.speaklearn.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,email)));
	}
}
