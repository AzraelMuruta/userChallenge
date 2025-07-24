package com.movchynnykov.userDataChallenge.dto;

import java.sql.Timestamp;
import java.util.List;

import com.movchynnykov.userDataChallenge.model.Phone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	
	private String id;
	private Timestamp created;
	private Timestamp lastLogin;
	private String token;
	private boolean isActive;
	private String name;
	private String email;
	private List<Phone> phones;
}