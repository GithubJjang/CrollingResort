package com.example.demo.dummy.test;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.dummy.test.errorZip.CustomNPE;
import com.example.demo.dummy.test.errorZip.ErrorDTO;


//@RestControllerAdvice
/*
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler 
 {
	
	
	// 1. throw new CustomNPE()시 , 발동
	//@ExceptionHandler(CustomNPE.class)
	public ResponseEntity<Object> test(CustomNPE e){
		
		ErrorDTO errorDTO = new ErrorDTO()
							    .buildName(e.getName())
							    .buildMessage(e.getMessage())
							    .buildStatus(e.getStatus().toString())
							    ;
		
		// CustomNPE를 그대로 보내서 쓸데없는 다량의 데이터가 넘어감.
		
		
		ResponseEntity<Object> response
		= new ResponseEntity(errorDTO,HttpStatusCode.valueOf(400));
		
		return response;
		
	}
	
	
}
*/