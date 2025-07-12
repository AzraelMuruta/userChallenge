package com.movchynnykov.userDataChallenge.controller;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.movchynnykov.userDataChallenge.response.ErrorResponse;


@ControllerAdvice
public class ResponseExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){
		ErrorResponse error = new ErrorResponse(new Timestamp(System.currentTimeMillis()), 400);
		error.setDetail(ex.getFieldError().getDefaultMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
