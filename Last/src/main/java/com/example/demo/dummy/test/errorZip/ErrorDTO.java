package com.example.demo.dummy.test.errorZip;

import org.springframework.http.HttpStatus;

public class ErrorDTO {
	
	private String name;
	private String status;
	private String message;
	
	// getter - setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	// build 패턴
	public ErrorDTO buildName(String name) {
		this.name=name;
		return this;
	}
	public ErrorDTO buildStatus(String status) {
		this.status=status;
		return this;
	}
	public ErrorDTO buildMessage(String message) {
		this.message=message;
		return this;
	}
	
	

}
