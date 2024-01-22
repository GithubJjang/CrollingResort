package com.example.demo.dummy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.dummy.model.High1Slope;
import com.example.demo.dummy.model.YongPyongSlope;
import com.example.demo.dummy.repository.High1Repository;

import jakarta.transaction.Transactional;

@Service
public class High1Service {

	@Autowired
	private High1Repository high1Repository;
	Boolean init = true;
	List<High1Slope> lst;
	Map<String, Boolean> isSlopeInlist;
	
	@Transactional
	public void setSlope(High1Slope high1Slope) {
		high1Repository.save(high1Slope);
		
	}
	
	// Dirty-checking을 이용한 update 쿼리 실행
	// 어차피, JPA의 엔티티 매니저가 Snapshot을 가지고 있어서, 변경사항을 자동으로 반영을 한다. <- 단, 한 트랜젝션 내에서만 유효!
	@Transactional
	public void updateSlope(High1Slope high1Slope) {
		// 이 경우는 slopeName과 slopeTag를 이용해서 식별을 해야한다.
		String slopeName = high1Slope.getSlopeName();
		String slopeTag = high1Slope.getSlopeTag();
		
		if(init) {
			// 항상 첫 실행시 초기화를 한다.
			initMap();
		}
		
		// 브라우저에서 찾은 슬로프가 DB에 있나요?
		isSlopein(high1Slope.getSlopeName());
		
		try {
			//System.out.println(slopeName+" "+slopeTag); -> 새로운 슬로프가 추가된 것이 확인이 된다면 -> NullPointerException
			High1Slope origHigh1Slope = high1Repository.findBySlopeNameAndSlopeTag(slopeName,slopeTag);
			
			// 모든 경우를 update를 해야할 것으로 가정을 한다.
			origHigh1Slope.setSlopeName(high1Slope.getSlopeName());
			origHigh1Slope.setSlopeTag(high1Slope.getSlopeTag());
			origHigh1Slope.set주간(high1Slope.get주간());
			origHigh1Slope.set야간(high1Slope.get야간());
			origHigh1Slope.setDifficulty(high1Slope.getDifficulty());;
			origHigh1Slope.set연장(high1Slope.get연장());
			origHigh1Slope.set표고차(high1Slope.get표고차());
			origHigh1Slope.set평균폭(high1Slope.get평균폭());
			origHigh1Slope.set경사각_평균(high1Slope.get경사각_평균());
			origHigh1Slope.set경사각_최대(high1Slope.get경사각_최대());
			origHigh1Slope.set혼잡(high1Slope.get혼잡());
			
		} catch (NullPointerException e) {
			// 새로 insert 쿼리를 보낸다.
			
			// 그리고 신규 슬로프 정보도 Map에 추가를 해준다.
			// 안해주면, 다시 삭제됨.
			isSlopeInlist.put(high1Slope.getSlopeName(), true);
			high1Repository.save(high1Slope);
		}

	}
	
	// 3. Map 초기화
	// DB의 슬로프 이름을 가져온다.
	private void initMap() {
		lst = high1Repository.findAll();//DB의 슬로프 이름을 가져온다.
		isSlopeInlist = new HashMap<String,Boolean>();
		
		
		for(High1Slope h: lst) {
			isSlopeInlist.put(h.getSlopeName(), false);
		}
	
		init=false;
	}
	
	//3-1. 슬로프가 있나요?
	private void isSlopein(String s) {
		
		if(isSlopeInlist.get(s)!=null) {
			// 해당 데이터가 있다 -> true로 바꾸자
			isSlopeInlist.replace(s, true);
		}
		
			
	}
	
	//3-2. 존재하지 않는 슬로프만 삭제를 한다.
	// 언제 함? -> 위 insert, update과정 다 끝난 후 진행
	@Transactional
	public void deleteSlope() {

		for (String key : isSlopeInlist.keySet()) {
			Boolean value = isSlopeInlist.get(key);
			if(!value) {
				// 삭제를 한다.
				high1Repository.deleteByslopeName(key);
			}
		}
		// 다음 사이클(1일 뒤)를 위해서 초기화를 한다.
		init=true;
	}
	
}
