package com.movchynnykov.userDataChallenge.Utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UtilsTest {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private String tokenTest;
	private String emailTest = "testmail1@test.com";
	private String passTest = "Test12";
	private byte[] salt;
	
	@BeforeAll
	void initTest() {
		tokenTest = jwtUtils.generateToken(emailTest);
		salt = PasswordUtils.getNewSalt();
	}
	
	@Test
	void validEmailShouldReturnAJWTToken() {
		assertNotNull(jwtUtils.generateToken(emailTest));
	}
	
	@Test
	void validJWTTokenShouldReturnCorrectEmail() {
		assertEquals(emailTest, jwtUtils.parseTokenForEmail(tokenTest));
	}
	
	@Test
	void saltFunctionShouldReturnByteArray() {
		assertTrue(PasswordUtils.getNewSalt() instanceof byte[]);
	}
	
	@Test
	void hashFunctionShouldReturnHashFromPasswordAndSalt() throws Exception {
		assertTrue(PasswordUtils.hashPassword(passTest, salt) instanceof byte[]);
	}
}
