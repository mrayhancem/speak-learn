package com.speaklearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speaklearn.domain.Role;
import com.speaklearn.domain.enums.RoleType;
import com.speaklearn.exception.ResourceNotFoundException;
import com.speaklearn.exception.message.ErrorMessage;
import com.speaklearn.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repository;
	
	public Role findByType(RoleType roleType) {
		return repository.findByType(roleType).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, roleType.name())));
	}

}
