package com.movchynnykov.userDataChallenge.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import com.movchynnykov.userDataChallenge.dto.UserDto;
import com.movchynnykov.userDataChallenge.dto.request.LoginRequest;
import com.movchynnykov.userDataChallenge.dto.request.SignUpRequest;

@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceImplTest {
	
	@Autowired
	private IUserService userService;
	
	private String oldToken;
	
	@BeforeAll
	void initTest() {
		String name = "Test Test";
		String email = "validMail1@valid.com";
		String password = "TOROJA12";
		SignUpRequest req = SignUpRequest.builder().name(name).email(email).password(password).build();
		try {
			oldToken = ((UserDto) userService.signUpUser(req).getBody()).getToken();
		} catch(Exception ex) {
			
		}
	}
	
	@Test
	void validSignUpRequestShouldReturnStatus200() {
		String name = "Test Test";
		String email = "validMail2@valid.com";
		String password = "TOROJA12";
		SignUpRequest req = SignUpRequest.builder().name(name).email(email).password(password).build();
		try {
			assertEquals(200, userService.signUpUser(req).getStatus());
		} catch(Exception ex) {
			
		}
	}
	
	@Test
	void repeatedSignUpEmailShouldReturnStatus403() {
		String name = "Test Test";
		String email = "validMail1@valid.com";
		String password = "TOROJA12";
		SignUpRequest req = SignUpRequest.builder().name(name).email(email).password(password).build();
		try {
			assertEquals(403, userService.signUpUser(req).getStatus());
		} catch(Exception ex) {
			
		}
	}
	
	@Test
	void validTokenShouldReturnValidMail() {
		LoginRequest req = LoginRequest.builder().token(oldToken).build();
		try {
			assertEquals("validMail1@valid.com", ((UserDto) userService.login(req).getBody()).getEmail());
		} catch(Exception ex) {
			
		}
	}
	
	@Test
	void invalidTokenShouldReturnStatus403() {
		LoginRequest req = LoginRequest.builder().token(oldToken+"tokenDestroyer").build();
		try {
			assertEquals(403, userService.login(req).getStatus());
		} catch(Exception ex) {
			
		}
	}
}
