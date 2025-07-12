package com.movchynnykov.userDataChallenge.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	
	private int status;
	private String message;
	private Object body;

}
