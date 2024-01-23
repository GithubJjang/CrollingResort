package com.example.demo.config.exception;



import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.config.exception.errors.custom.CustomNPE;
import com.example.demo.dto.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	// 1. Client가 없는 정보를 조회하는 경우.
	@ExceptionHandler(CustomNPE.class)
	public ResponseEntity<Object> resortNull(CustomNPE e){
		ErrorDTO dto = new ErrorDTO()
						   .buildType(e.getType())
						   .buildMessage(e.getMessage())
						   .buildStatus(e.getHttpStatus())
						   ;
						   
		ResponseEntity<Object> responseError = new ResponseEntity<Object>(dto, HttpStatusCode.valueOf(400));
		return responseError;
		
	}

}
