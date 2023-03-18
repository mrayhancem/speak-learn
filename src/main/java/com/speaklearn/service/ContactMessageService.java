package com.speaklearn.service;

import org.springframework.stereotype.Service;

import com.speaklearn.domain.ContactMessage;
import com.speaklearn.repository.ContactMessageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactMessageService {
	
	private ContactMessageRepository contactMessageRepository;
	
	public void saveMessage(ContactMessage contactMessage) {
		contactMessageRepository.save(contactMessage);
	}
}
