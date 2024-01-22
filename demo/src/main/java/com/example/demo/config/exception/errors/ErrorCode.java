package com.example.demo.config.exception.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.example.demo.config.exception.errors.custom.CustomNPE;

public interface ErrorCode {

	// getter - setter
	public int getType();
	public void setType(int type);
	
	public String getMessage();
	public void setMessage(String message);
	
	public HttpStatus getHttpStatus();
	public void setHttpStatus(HttpStatus errorStatus);
	

}
