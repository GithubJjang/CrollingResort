package com.example.demo.dummy.test.errorZip;

import org.springframework.http.HttpStatus;

// 이유: 부모의 메서드와 그 변수를 그대로 쓰면 될듯.
// 굳이 interface와 abstract으로 구현할 필요가 있을까??? <- 번거로움.
// 여기서 extends가 더 일어날까? <- x
public class CustomNPE extends NullPointerException implements ErrorCode   {
	private String name;
	private HttpStatus status;
	private String message;
	
	// 빌더 패턴
	public CustomNPE buildName(String name) {
		this.name=name;
		return this;
	}
	public CustomNPE buildStatus(HttpStatus status) {
		this.status=status;
		return this;
	}
	public CustomNPE buildMessage(String message) {
		this.message=message;
		return this;
	}
	
	// getter - setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
