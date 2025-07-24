package com.movchynnykov.userDataChallenge.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	
	@Id
	private String uuid;
	private String name;
	private String email;
	private byte[] password;
	private byte[] salt;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Phone> phones;
	
	private boolean isActive;
	private String token;
	private Timestamp created;
	private Timestamp lastLogin;
}
