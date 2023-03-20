package com.speaklearn.domain.enums;

public enum RoleType {
	
	
	ROLE_CUSTOMER("Student"),
	ROLE_TEACHER("Teacher"),
	ROLE_ADMIN("Administrator");
	
	private String name;
	
	
	private RoleType(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}
	
	
	

}
