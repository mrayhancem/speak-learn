package com.speaklearn.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.speaklearn.domain.ContactMessage;
import com.speaklearn.dto.ContactMessageDTO;
import com.speaklearn.dto.request.ContactMessageRequest;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {
	
	@Mapping(target = "id", ignore = true)
	ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);
	
	
	ContactMessageDTO contactMessageToContactMessageDTO(ContactMessage contactMessage);
	
	List<ContactMessageDTO> contactMessageToContactMessageDTO(List<ContactMessage> contactMessage);
}
