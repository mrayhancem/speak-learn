package com.speaklearn.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_user")
@Entity
public class User extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length= 50 , nullable = false)
	private String firstName;
	
	@Column(length= 50 , nullable = false)
	private String lastName;
	
	@Column(length= 80 , nullable = false, unique = true)
	private String email;
	
	@Column(length= 120 , nullable = false)
	private String password;
	
	@Column(length= 14 , nullable = false)
	private String phoneNumber;
	
	@Column(length= 100 , nullable = false)
	private String address;
	
	@Column(length= 15 , nullable = false)
	private String zipCode;
	
	@Column(nullable = false)
	private Boolean builtIn = false;
	
	
	@ManyToMany
	@JoinTable(name="t_user_role" , joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
}
