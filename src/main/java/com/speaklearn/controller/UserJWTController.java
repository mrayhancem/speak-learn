package com.speaklearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.speaklearn.dto.request.RegisterRequest;
import com.speaklearn.dto.response.BaseResponse;
import com.speaklearn.dto.response.ResponseMessage;
import com.speaklearn.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserJWTController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<BaseResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
		userService.registerUser(registerRequest);
		
		BaseResponse response = new BaseResponse(ResponseMessage.USER_CREATED_SUCCESSFULLY,true);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
}
