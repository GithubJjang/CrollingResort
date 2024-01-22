package com.example.demo.Testcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.exception.errors.custom.CustomNPE;
import com.example.demo.dto.SlopeDTO;
import com.example.demo.model.Resort;
import com.example.demo.model.Slope;

import com.example.demo.service.user.UserResortService;
import com.example.demo.service.user.UserSlopeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class UserController {
	
	@Autowired
	private UserSlopeService userSlopeService;
	
	@Autowired
	private UserResortService userResortService;
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path = "/resorts")
	public List<Resort> showResortList() throws JsonProcessingException {
		// 국내 모든 스키장 리조트 보여주기.
		// 버튼을 클릭하면, 해당 리조트의 id를 넘겨준다.(수정)
		List<Resort>findAllResorts=userResortService.getResorts();
		return findAllResorts;
	}
	
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path ="/resorts/{id}")
	public Resort getResort(@PathVariable(value = "id") int resortId) throws JsonProcessingException {
		// 특정 id에 해당하는 리조트만 가져오기.
		// 리조트명 + id만 가지고 있는 상태
		
		// 주의. Client가 없는 리조트 조회 -> return값이 NULL 이므로, 오류발생
		Resort getResort = Optional.ofNullable(userResortService.getResort(resortId))
											 .orElseGet(()->{
												 throw new CustomNPE()
												 	       .buildType(HttpStatus.BAD_REQUEST.value())
												 	       .buildStatus(HttpStatus.BAD_REQUEST)
												 	       .buildMessage("해당되는 리조트는 없습니다. 입력하신 정보를 다시 확인해주세요.")
												 	       ;
											 });
		return getResort;
	}
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(path="/resorts/{id}/slopes")
	public List<SlopeDTO> getSlopeInfo(
			@RequestParam(value = "day")String day,
			@PathVariable(value = "id") int resortId) throws JsonProcessingException {
			// resortId  <- 특정리조트 조회 목적
			// day <- 특정 날짜의 슬로프 조회 목적.

		Resort resort = userResortService.getResort(resortId);
		List<Slope> rawSlopeList = userSlopeService.getRelatedSlopesWithDate(resort, day);
		
		// 주의! -> 위 가져온 슬로프List에 아무것도 없는 경우, 예외처리가 필요.
		if(rawSlopeList.size()==0) {
			// 강제 예외 발생
			throw new CustomNPE()
				      .buildType(HttpStatus.BAD_REQUEST.value())
				      .buildMessage("해당되는 리조트 또는 날짜가 없습니다. 입력하신 정보를 다시 확인해주세요.")
				      .buildStatus(HttpStatus.BAD_REQUEST);
		}
		
		
		List<SlopeDTO> slopeList = new ArrayList<>();
		for(Slope s: rawSlopeList) {
			slopeList.add(new SlopeDTO().buildId(s.getId())
										.buildResort(s.getResort().getId())
										.buildDifficulty(s.getDifficulty())
										.buildSlopeName(s.getSlopeName())
										.buildDay(s.getDawn())
										.buildNight(s.getNight())
										.buildDawn(s.getDawn())
										.buildDate(s.getDate())			
			);
		}
		
		return slopeList;
	}
}
