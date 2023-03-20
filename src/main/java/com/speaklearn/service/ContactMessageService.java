package com.speaklearn.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.speaklearn.domain.ContactMessage;
import com.speaklearn.exception.ResourceNotFoundException;
import com.speaklearn.exception.message.ErrorMessage;
import com.speaklearn.repository.ContactMessageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactMessageService {
	
	private ContactMessageRepository contactMessageRepository;
	
	public void saveMessage(ContactMessage contactMessage) {
		contactMessageRepository.save(contactMessage);
	}
	
	public List<ContactMessage> getAll(){
		return contactMessageRepository.findAll();
	}
	
	public Page<ContactMessage> getAll(Pageable pageable){
		return contactMessageRepository.findAll(pageable);
	}
	
	public ContactMessage getContactMessageById(Long id) {
		return contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
	}
	
	public void deleteContactMessage(Long id)
	{
		ContactMessage message = getContactMessageById(id);
		contactMessageRepository.delete(message);
	}
	
	public void updateContactMessage(Long id, ContactMessage newContactMessage)
	{
		ContactMessage oldMessage = getContactMessageById(id);
		oldMessage.setName(newContactMessage.getName());
		oldMessage.setBody(newContactMessage.getBody());
		oldMessage.setEmail(newContactMessage.getEmail());
		oldMessage.setSubject(newContactMessage.getSubject());
		
		contactMessageRepository.save(oldMessage);
	}
}
