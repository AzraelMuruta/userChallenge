package com.movchynnykov.userDataChallenge.services;

import com.movchynnykov.userDataChallenge.dto.request.LoginRequest;
import com.movchynnykov.userDataChallenge.dto.request.SignUpRequest;
import com.movchynnykov.userDataChallenge.dto.response.UserResponse;

public interface IUserService {
	
	public UserResponse signUpUser(SignUpRequest user) throws Exception;
	
	public UserResponse login(LoginRequest user) throws Exception;

}
