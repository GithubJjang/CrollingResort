package com.example.demo.dto;


import org.springframework.http.HttpStatus;



public class ErrorDTO {
	
	private int type;
	private String message;
	private HttpStatus errorStatus;
	
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
	public HttpStatus getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(HttpStatus errorStatus) {
		this.errorStatus = errorStatus;
	}
	
	
	// 빌더 패턴
	public ErrorDTO buildType(int type) {
			this.type=type;
			return this;
	}
	public ErrorDTO buildStatus(HttpStatus errorStatus) {
			this.errorStatus=errorStatus;
			return this;
	}
	public ErrorDTO buildMessage(String message) {
			this.message=message;
			return this;
	}
	
	

}
