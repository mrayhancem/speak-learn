package com.speaklearn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speaklearn.domain.ContactMessage;
import com.speaklearn.dto.request.ContactMessageRequest;
import com.speaklearn.dto.response.BaseResponse;
import com.speaklearn.mapper.ContactMessageMapper;
import com.speaklearn.service.ContactMessageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/contactmessage")
@AllArgsConstructor
public class ContactMessageController {
	
	private ContactMessageService contactMessageService;
	
	private ContactMessageMapper contactMessageMapper;
	
	//save the incoming message into database
	@PostMapping("/visitors")
	public ResponseEntity<BaseResponse> createMessage(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
		
		ContactMessage message = contactMessageMapper.contactMessageRequestToContactMessage(contactMessageRequest);
		
		contactMessageService.saveMessage(message);
		
		BaseResponse response = new BaseResponse("ContactMessage successfully  created", true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
}
