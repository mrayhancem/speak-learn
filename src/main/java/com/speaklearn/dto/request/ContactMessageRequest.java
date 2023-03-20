package com.speaklearn.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessageRequest {
	
	@Size(min=3, max=50, message = "Your name '${validatedValue}' must be between {min} and {max} chars long")
	@NotBlank(message = "Please provide a name!")	
	private String name;
	
	@Size(min=5, max=50, message = "Subject '${validatedValue}' must be between {min} and {max} chars long")
	@NotBlank(message = "Please provide a subject!")
	private String subject;
	
	@Size(min=20, max=200, message = "Message Body '${validatedValue}' must be between {min} and {max} chars long")
	@NotBlank(message = "Please provide a message body!")
	private String body;
	
	
	@Email(message = "Please provide a valid email")
	private String email;
}
