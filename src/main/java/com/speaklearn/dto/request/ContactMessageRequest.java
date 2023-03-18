package com.speaklearn.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageRequest {
	
	@NotBlank(message = "Please provide a name!")	
	private String name;
	
	@NotBlank(message = "Please provide a subject!")
	private String subject;
	
	@NotBlank(message = "Please provide a message body!")
	private String body;
	
	@NotBlank(message = "Please provide an email!")
	private String email;
}
