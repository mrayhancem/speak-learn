package com.speaklearn.controller;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.speaklearn.domain.ContactMessage;
import com.speaklearn.dto.ContactMessageDTO;
import com.speaklearn.dto.request.ContactMessageRequest;
import com.speaklearn.dto.response.BaseResponse;
import com.speaklearn.dto.response.ResponseMessage;
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
		
		BaseResponse response = new BaseResponse(ResponseMessage.CONTACTMESSAGE_SAVE_RESPONSE_MESSAGE, true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	//getting contact messages from database
	
	@GetMapping
	public ResponseEntity<List<ContactMessageDTO>> getAllContactMessage(){
		//getting the data from database
		List<ContactMessage> contactMessageList = contactMessageService.getAll();
		//converting the data to dto for frontend
		List<ContactMessageDTO> contactMessageDTOList = contactMessageMapper.contactMessageToContactMessageDTO(contactMessageList);
		
		return ResponseEntity.ok(contactMessageDTOList);
	}
	
	
	@GetMapping("/pages")
	public ResponseEntity<Page<ContactMessageDTO>> getAllContactMessageWithPage(@RequestParam("page") int page,
																				@RequestParam("size") int size,
																				@RequestParam("sort") String prop,
																				@RequestParam(value = "direction", required = false, defaultValue = "DESC" ) Direction direction){
		
		//creating pageable object with the given parameters
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
		
		//getting contact messages as raw in pageable format.
		Page<ContactMessage> contactMessageDTOWithPage = contactMessageService.getAll(pageable);
		
		//converting raw contactmessage pages to DTO 
		Page<ContactMessageDTO> pageDTO = getPageDTO(contactMessageDTOWithPage);
		
		return ResponseEntity.ok(pageDTO);
	}
	
	//get one contactmessage by id using path parameters
	
	@GetMapping("/{id}")
	public ResponseEntity<ContactMessageDTO> getContactMessageWithPath(@PathVariable("id") Long id){
		
		ContactMessage cmessage = contactMessageService.getContactMessageById(id);
		ContactMessageDTO cmsgDTO = contactMessageMapper.contactMessageToContactMessageDTO(cmessage);
		
		return ResponseEntity.ok(cmsgDTO);
		
		
	}
	
	//get one contactmessage by id using request parameters
	@GetMapping("/request")
	public ResponseEntity<ContactMessageDTO> getContactMessageWithRequest(@RequestParam("id") Long id)
	{
			
		ContactMessage cmessage = contactMessageService.getContactMessageById(id);
		ContactMessageDTO cmsgDTO = contactMessageMapper.contactMessageToContactMessageDTO(cmessage);
			
		return ResponseEntity.ok(cmsgDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<BaseResponse> deleteContactMessage(@PathVariable("id") Long id)
	{
		contactMessageService.deleteContactMessage(id);
		
		BaseResponse response = new BaseResponse(String.format(ResponseMessage.CONTACTMESSAGE_DELETE_SUCCESS,id), true);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse> updateContactMessage(@Valid @RequestBody ContactMessageRequest updatedMessage, @PathVariable("id") Long id)
	{
		
		ContactMessage newMessage = contactMessageMapper.contactMessageRequestToContactMessage(updatedMessage);
		contactMessageService.updateContactMessage(id, newMessage);
		
		BaseResponse response = new BaseResponse(String.format(ResponseMessage.CONTACTMESSAGE_UPDATE_SUCCESS,id), true);
		
		return ResponseEntity.ok(response);
	}
		
	
	
	
	private Page<ContactMessageDTO> getPageDTO(Page<ContactMessage> contactMessagePage){
		
		//using map to individually convert all contact messages to dto, +using functional interface of java.util class.
		Page<ContactMessageDTO> contactMessageDTOPage = contactMessagePage.map(new Function<ContactMessage, ContactMessageDTO>(){

			@Override
			public ContactMessageDTO apply(ContactMessage t) {
				//using single contactmessage to contactmessageDto mapper in mapper class.
				return contactMessageMapper.contactMessageToContactMessageDTO(t);
			}
			
		});
		
		return contactMessageDTOPage ;
		
	}
	
	
	
	
}
