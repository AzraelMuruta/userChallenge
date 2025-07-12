package com.movchynnykov.userDataChallenge.interfaces;

import com.movchynnykov.userDataChallenge.request.LoginRequest;
import com.movchynnykov.userDataChallenge.request.SignUpRequest;
import com.movchynnykov.userDataChallenge.response.UserResponse;

public interface IUserService {
	
	public UserResponse signUpUser(SignUpRequest user) throws Exception;
	
	public UserResponse login(LoginRequest user) throws Exception;

}
