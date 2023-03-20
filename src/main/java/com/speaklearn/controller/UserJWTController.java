package com.speaklearn.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserJWTController {
	
	
	
	@PostMapping("/register")
	public ResponseEntity<BaseResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
		
	}
}
