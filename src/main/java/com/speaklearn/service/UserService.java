package com.speaklearn.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.speaklearn.domain.Role;
import com.speaklearn.domain.User;
import com.speaklearn.domain.enums.RoleType;
import com.speaklearn.dto.request.RegisterRequest;
import com.speaklearn.exception.ResourceNotFoundException;
import com.speaklearn.exception.UserAlreadyExistException;
import com.speaklearn.exception.message.ErrorMessage;
import com.speaklearn.repository.UserRepository;

@Service
public class UserService {
	
	
	private UserRepository userRepository;
	
	
	private RoleService roleService;
	
	
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.userRepository = userRepository;
	}
	
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,email)));
	}
	
	public void registerUser(RegisterRequest registerRequest) {
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new UserAlreadyExistException(String.format(ErrorMessage.USER_ALREADY_EXIST, registerRequest.getEmail()));
		}
		
		User user = new User();
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setAddress(registerRequest.getAddress());
		user.setEmail(registerRequest.getEmail());
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setZipCode(registerRequest.getZipCode());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setBuiltIn(false);
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByType(RoleType.ROLE_CUSTOMER));
		user.setRoles(roles);
		userRepository.save(user);
	}
}
