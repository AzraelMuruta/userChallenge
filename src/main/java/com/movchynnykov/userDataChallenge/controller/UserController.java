package com.movchynnykov.userDataChallenge.controller;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movchynnykov.userDataChallenge.Utils.Constantes;
import com.movchynnykov.userDataChallenge.interfaces.IUserService;
import com.movchynnykov.userDataChallenge.request.LoginRequest;
import com.movchynnykov.userDataChallenge.request.SignUpRequest;
import com.movchynnykov.userDataChallenge.response.ErrorResponse;
import com.movchynnykov.userDataChallenge.response.UserResponse;


@RestController
@CrossOrigin(value = "*")
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/sign-up")
	public UserResponse signUp(@Valid @RequestBody SignUpRequest req) {
		try {
			return userService.signUpUser(req);
		} catch(Exception ex) {
			ex.printStackTrace();
			ErrorResponse error = new ErrorResponse(new Timestamp(System.currentTimeMillis()), 500);
			error.setDetail(ex.getMessage());
			return UserResponse.builder().status(500).message(Constantes.errorInternalServerError).body(error).build();
		}
	}
	
	@PostMapping("/login")
	public UserResponse login(@Valid @RequestBody LoginRequest req) {
		try {
			return userService.login(req);
		} catch(Exception ex) {
			ex.printStackTrace();
			ErrorResponse error = new ErrorResponse(new Timestamp(System.currentTimeMillis()), 500);
			error.setDetail(ex.getMessage());
			return UserResponse.builder().status(500).message(Constantes.errorInternalServerError).body(error).build();
		}
	}
}
