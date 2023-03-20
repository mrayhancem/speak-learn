package com.speaklearn.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
	
	@Size(max = 50)
	@NotBlank(message = "Please provide your First name")
	private String firstName;
	
	@Size(max = 50)
	@NotBlank(message = "Please provide your Last name")
	private String lastName;
	
	@Email(message = "Please provide valid email")
	@Size(min = 5, max = 80)
	private String email;
	
    @Size(min = 4, max = 20,message="Please Provide Correct Size for Password")
    @NotBlank(message = "Please provide your password")
	private String password;
	
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please provide valid phone number")
    @Size(min = 14, max = 14)
    @NotBlank(message = "Please provide your phone number")
	private String phoneNumber; 

    @Size(max = 100)
    @NotBlank(message = "Please provide your address")
	private String address; 
	
    @Size(max = 15)
    @NotBlank(message = "Please provide your address")
	private String zipCode; 
	
}
