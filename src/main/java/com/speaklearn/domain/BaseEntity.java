package com.speaklearn.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public class BaseEntity {
	
	@Version
	private Long version;
}
