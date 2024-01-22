package com.example.demo.config.exception.errors.custom;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.example.demo.config.exception.errors.ErrorCode;

public class CustomNPE extends NullPointerException implements ErrorCode{
	private int type;
	private String message;
	private HttpStatus errorStatus;
	
	
	// 반드시 하위 기능을 구현해야만 한다.
	// getter - setter
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getHttpStatus() {
		return errorStatus;
	}
	public void setHttpStatus(HttpStatus errorStatus) {
		this.errorStatus = errorStatus;
	}
	
	// 빌더 패턴
	public CustomNPE buildType(int type) {
		this.type=type;
		return this;
	}
	public CustomNPE buildStatus(HttpStatus errorStatus) {
		this.errorStatus=errorStatus;
		return this;
	}
	public CustomNPE buildMessage(String message) {
		this.message=message;
		return this;
	}
}
