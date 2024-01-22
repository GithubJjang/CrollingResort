package com.example.demo.dummy.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dummy.test.errorZip.CustomNPE;
import com.example.demo.model.Resort;
import com.example.demo.service.user.UserResortService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Autowired
	private UserResortService userResortService;
	
	@GetMapping("/test")
	public String doTest1() throws JsonProcessingException {
		// Optional을 통해서 null인지 확인을 한다.
		// 만약 null일 경우, Exception을 발생시킨다.
		Resort getResort = Optional.ofNullable(userResortService.getResort(100))
												.orElseGet(()->{
													throw new CustomNPE()
															  .buildName(NullPointerException.class.toString())
															  .buildMessage("해당 리조트는 존재하지 않습니다. 다시 확인해주세요.")
															  .buildStatus(HttpStatus.BAD_REQUEST);
												});
		// 
		ObjectMapper obj = new ObjectMapper();
		
		String json = obj.writeValueAsString(getResort);
		return json;

	}
	

}
