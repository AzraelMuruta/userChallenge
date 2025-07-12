package com.movchynnykov.userDataChallenge.response;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ErrorResponse {
	private Timestamp timestamp;
	private int codigo;
	private String detail;
	
	public ErrorResponse(Timestamp timestamp, int codigo) {
		this.timestamp = timestamp;
		this.codigo = codigo;
	}
	
}

