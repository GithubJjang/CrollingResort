package com.example.demo.dummy.test.errorZip;

import org.springframework.http.HttpStatus;

// 반드시 클라이언트에게 전해져야하는 필수 기능들
// 기능 고정
// 향후 여러개의 에러를 하나로 묶기 유리.
public interface ErrorCode{

	// getter - setter
	public String getName();
	public void setName(String name);
	
	public HttpStatus getStatus();
	public void setStatus(HttpStatus status);

	public String getMessage();
	public void setMessage(String message);
	
}
